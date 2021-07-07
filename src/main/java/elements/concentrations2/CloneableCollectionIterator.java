package elements.concentrations2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

class CloneableCollectionIterator<E> implements Iterator<E> {

    private final ListIterator<E> listIterator;
    private final ArrayList<E> collection;
    private int index;

    public CloneableCollectionIterator(Collection<E> collection) {
        this(collection, 0);
    }

    CloneableCollectionIterator(Collection<E> collection, int index) {
        this.collection = new ArrayList<E>(collection);
        this.listIterator = this.collection.listIterator(index);
        this.index = index;
    }

    public CloneableCollectionIterator clone() {
        return new CloneableCollectionIterator(collection, index);
    }

    @Override
    public boolean hasNext() {
        return listIterator.hasNext();
    }

    @Override
    public E next() {
        this.index++;
        return listIterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
