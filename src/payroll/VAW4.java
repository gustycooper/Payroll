package payroll;

public class VAW4 {
    private int personalExemptions;
    private int blindExemptions;
    private Dollar additionalWithHold;
    private boolean exempt;

    public VAW4(int personalExemptions, int blindExemptions, Dollar additionalWithHolding, boolean exempt) {
        this.personalExemptions = personalExemptions;
        this.blindExemptions = blindExemptions;
        this.additionalWithHold = additionalWithHolding;
        this.exempt = exempt;
    }

    public boolean exempt() { return exempt; }

    public int getPersonalExemptions() { return personalExemptions; }

    public int getBlindExemptions() { return blindExemptions; }

    public Dollar getAdditionalWithHold() { return additionalWithHold; }

    public String toString() {
        return "" + personalExemptions + " " + blindExemptions + " " + additionalWithHold + " " + exempt;
    }
}
