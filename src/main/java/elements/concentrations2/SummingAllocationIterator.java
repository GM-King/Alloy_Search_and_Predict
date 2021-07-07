package elements.concentrations2;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//@NotThreadSafe: hasNext() and next() must be used from the same thread
public class SummingAllocationIterator<E> implements Iterator<Map<E, Double>> {

    public static final Map NO_MORE_ALLOCATIONS = Collections.EMPTY_MAP;
    public static final int QUEUE_BATCH_SIZE = 1000;

    private final Map<E, CloneableDoubleIteratorWithinBounds> elementAllocators;
    private final double requiredSum;
    private BlockingQueue<Map<E, Double>> allocationQueue = new ArrayBlockingQueue(QUEUE_BATCH_SIZE);
    private Map<E, Double> next;

    public static <E> SummingAllocationIterator newInstance(Map<E, CloneableDoubleIteratorWithinBounds> allocators,
                                                            double sum) {
        SummingAllocationIterator iterator = new SummingAllocationIterator(allocators, sum);
        iterator.startAllocating();
        return iterator;
    }

    private SummingAllocationIterator(Map<E, CloneableDoubleIteratorWithinBounds> elementAllocators,
                                      double requiredSum) {
        this.elementAllocators = elementAllocators;
        this.requiredSum = requiredSum;
    }

    private void startAllocating() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CloneableCollectionIterator<Map.Entry<E, CloneableDoubleIteratorWithinBounds>> iterator
                    = new CloneableCollectionIterator<>(new LinkedList<>(elementAllocators.entrySet()));

                allocationIterator(
                        new HashMap<E, Double>(),
                        iterator,
                        0);

                putInQueue(NO_MORE_ALLOCATIONS);
            }
        }).start();
    }

    private void allocationIterator(Map<E, Double> partialResult,
                                    CloneableCollectionIterator<Map.Entry<E, CloneableDoubleIteratorWithinBounds>> iterator,
                                    double partialSum) {
        Map.Entry<E, CloneableDoubleIteratorWithinBounds> concentrationIteratorEntry = iterator.next();
        E element = concentrationIteratorEntry.getKey();
        CloneableDoubleIteratorWithinBounds iteratorWithinBounds = concentrationIteratorEntry.getValue().clone();

        if (partialSum > requiredSum) {
            return;
        }

        if (!iterator.hasNext()) {
            double concentration = requiredSum - partialSum;
            if (iteratorWithinBounds.isInRange(concentration)) {
                partialResult.put(element, concentration);
                putInQueue(partialResult);
            }

            return;
        }

        while (iteratorWithinBounds.hasNext()) {
            Double concentration = iteratorWithinBounds.next();

            HashMap<E, Double> currentAllocation = new HashMap<>(partialResult);
            currentAllocation.put(element, concentration);

            allocationIterator(
                    currentAllocation,
                    iterator.clone(),
                    partialSum + concentration
            );
        }
    }

    private void putInQueue(Map<E, Double> partialResult) {
        try {
            allocationQueue.put(partialResult);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        if (next == null) {
            next = takeFromQueue();
        }

        return next != NO_MORE_ALLOCATIONS;
    }

    @Override
    public Map<E, Double> next() {
        Map<E, Double> result = next;
        next = null;
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private Map<E, Double> takeFromQueue() {
        try {
            return allocationQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
}
