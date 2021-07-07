package elements.io;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResultCSV implements AutoCloseable {
    private final BufferedWriter resultFile;
    private AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    private CountDownLatch latch = new CountDownLatch(1);

    public ResultCSV(String filename) {
        try {
            int bufferSize = 1024 * 1024;
            this.resultFile = new BufferedWriter(
                    new FileWriter(filename),
                    bufferSize
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            if (resultFile != null) {
                resultFile.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void write(CSVRow csvRow) {
        if (latch.getCount() > 0) {
            boolean updated = atomicBoolean.compareAndSet(false, true);
            if (updated) {
                printRow(csvRow.getHeadings());
                latch.countDown();
            }
        }
        awaitLatch();

        printRow(csvRow.getValues());
    }

    private void printRow(Collection<?> headings) {
        try {
            resultFile.write(commaSeparate(headings) + "\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String commaSeparate(Collection<?> values) {
        StringBuilder builder = new StringBuilder();
        Iterator<?> iterator = values.iterator();
        while (iterator.hasNext()) {
            String field = iterator.next().toString();
            builder.append(StringUtils.leftPad(field, 12));
            if (iterator.hasNext()) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    private void awaitLatch() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
