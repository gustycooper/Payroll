package payroll;

public class W4 {
    private int totalNumAllowances;
    private Dollar additionalWithHold;
    private boolean exempt;
    private String maritalStatus; // TODO - create enum for W4 marital status

    public W4(int totallAllowances, Dollar additionalWithHolding, String maritalStatus, boolean exempt) {
        totalNumAllowances = totallAllowances;
        additionalWithHold = additionalWithHolding;
        this.maritalStatus = maritalStatus;
        this.exempt = exempt;
    }

    public boolean exempt() { return exempt; }

    public String getMaritalStatus() { return maritalStatus; }

    public int getTotalNumAllowances() { return totalNumAllowances; }

    public Dollar getAdditionalWithHold() { return additionalWithHold; }

    public String toString() {
        return "" + totalNumAllowances + " " + additionalWithHold + " " + exempt + " " + maritalStatus;
    }
}
