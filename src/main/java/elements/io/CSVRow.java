package elements.io;

import java.util.Collection;

public interface CSVRow {
    Collection<String> getHeadings();

    Collection<Object> getValues();
}
