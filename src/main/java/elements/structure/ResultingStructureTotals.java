package elements.structure;

import java.util.HashMap;
import java.util.Map;

public class ResultingStructureTotals {

    private Map<ResultingStructure, Integer> totals;

    public ResultingStructureTotals() {
        // setValue the total to zero for each structure type
        this.totals = new HashMap<>();
        for (ResultingStructure resultingStructure : ResultingStructure.values()) {
            totals.put(resultingStructure, 0);
        }
    }

    public void handleResult(ResultingStructure resultingStructure) {
        incrementTotal(resultingStructure);
    }

    public Integer get(ResultingStructure structure) {
        return totals.get(structure);
    }

    private void incrementTotal(ResultingStructure resultingStructure) {
        Integer total = totals.get(resultingStructure);
        totals.put(resultingStructure, total + 1);
    }

    @Override
    public String toString() {
        return totals.toString();
    }

}
