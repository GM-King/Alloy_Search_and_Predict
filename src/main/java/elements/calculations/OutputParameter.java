package elements.calculations;

public enum OutputParameter {
    /*
        result.setValue("omega", omega);
        result.setValue("delta", delta);
        result.setValue("gam", gamma);
        result.setValue("VEC", vec);
        result.setValue("hmixSS", hmixSS);
        result.setValue("hmixAm", hmixAm);
        result.setValue("smix", smix);
        result.setValue("tm", tm);
        result.setValue("nxst", nxst);
        result.setValue("ssCor", ssCor);
        result.setValue("ss", ss);
        result.setValue("hmixL", hmixL);
     */
    PHI("ϕ", "phi"), //what should this be?
    DELTA("δ", "delta"),
    PRICE("Price", "gam"),
    TM("tₘ", "tm"),
    TMAX("tₘₐₓ", "tmax"), //what should this be?
    PTEMP("pₜₑₘₚ", "ptemp"), //what should this be?
    NXST("nxst", "nxst"),
    K("k", "k"), //what should this be?
    OMEGA("ω", "omega"),
    GAMMA("γ", "gam"),
    LAMBDA("λ", "lambda"); //what should this be?

    private final String displayText;
    private String columnName;

    private OutputParameter(String displayText,
                            String columnName) {
        this.displayText = displayText;
        this.columnName = columnName;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getColumnName() {
        return columnName;
    }
}
