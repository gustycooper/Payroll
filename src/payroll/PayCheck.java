package payroll;

public class PayCheck {
    private Dollar grossPay;
    private Dollar netPay;
    private Dollar federalTaxWithHolding;
    private Dollar SSWithHolding;
    private Dollar medicareWithHolding;
    private Dollar VATaxWithHolding;
    private String payDate; // TODO - change payDate to be Date
    private String startDate;
    private String endDate;
    private Dollar hourlyRate;
    private double hoursWorked;

    public PayCheck(Dollar hourlyRate, double hoursWorked) {
        this.grossPay = new Dollar("" + (hoursWorked * hourlyRate.getCost()));
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    public Dollar getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(Dollar grossPay) {
        this.grossPay = grossPay;
    }

    public Dollar getNetPay() {
        return netPay;
    }

    public void setNetPay(Dollar netPay) {
        this.netPay = netPay;
    }

    public Dollar getFederalTaxWithHolding() {
        return federalTaxWithHolding;
    }

    public void setFederalTaxWithHolding(Dollar federalTaxWithHolding) {
        this.federalTaxWithHolding = federalTaxWithHolding;
    }

    public Dollar getSSWithHolding() {
        return SSWithHolding;
    }

    public void setSSWithHolding(Dollar SSWithHolding) {
        this.SSWithHolding = SSWithHolding;
    }

    public Dollar getMedicareWithHolding() {
        return medicareWithHolding;
    }

    public void setMedicareWithHolding(Dollar medicareWithHolding) {
        this.medicareWithHolding = medicareWithHolding;
    }

    public Dollar getVATaxWithHolding() {
        return VATaxWithHolding;
    }

    public void setVATaxWithHolding(Dollar VATaxWithHolding) {
        this.VATaxWithHolding = VATaxWithHolding;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String toString() {
        return  "Paydate: " + payDate + " " +
                "Pay Period: " + startDate + " to " + endDate + "\n" +
                "Hourly Rate: $" + hourlyRate + " " +
                "Hours Worked: " + hoursWorked + "\n" +
                "Grosspay: " + grossPay + " " +
                "Netpay: " + netPay + "\n" +
                "Federal Tax Withholding: " + federalTaxWithHolding + "\n" +
                "SS Withholding: " + SSWithHolding + "\n" +
                "Medicare Withholding: " + medicareWithHolding + "\n" +
                "VA Tax Withholding: " + VATaxWithHolding;

    }
}
