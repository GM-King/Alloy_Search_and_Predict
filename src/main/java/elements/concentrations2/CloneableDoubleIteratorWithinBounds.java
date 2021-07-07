package elements.concentrations2;

import java.util.Iterator;

public interface CloneableDoubleIteratorWithinBounds extends Iterator<Double> {
    CloneableDoubleIteratorWithinBounds clone();
    boolean isInRange(double concentration);
}
