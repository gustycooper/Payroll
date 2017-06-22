package payroll;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String fn;
    private String ln;
    private String ssn;
    private String street;
    private String city;
    private String state;
    private W4 w4;
    private VAW4 vaw4;
    private Dollar hourlyRate;
    private List<PayCheck> payCheckList;

    public Employee(String fn, String ln, String ssn, String street, String city, String state, Dollar hourlyRate, W4 w4, VAW4 vaw4) {
        this.fn = fn;
        this.ln = ln;
        this.ssn = ssn;
        this.street = street;
        this.city = city;
        this.state = state;
        this.hourlyRate = hourlyRate;
        this.w4 = w4;
        this.vaw4 = vaw4;
        this.payCheckList = new ArrayList<>();
    }

    public W4 getW4() { return w4; }

    public VAW4 getVAW4() { return vaw4; }

    public void addPayCheck(PayCheck pc) { payCheckList.add(pc); }

    public void addPayCheck(double hoursWorked, String payDate, String startDate, String endDate) {
        Dollar grossPay = new Dollar("" + (hoursWorked * hourlyRate.getCost()));

        PayCheck pc = new PayCheck(hourlyRate, hoursWorked);
        pc.setPayDate(payDate);
        pc.setStartDate(startDate);
        pc.setEndDate(endDate);
        pc.setFederalTaxWithHolding(FederalWithHolding.computeWithHolding(this, grossPay));
        pc.setMedicareWithHolding(MedicareWithHolding.computeWithHolding(this, grossPay));
        pc.setSSWithHolding(SSWithHolding.computeWithHolding(this, grossPay));
        pc.setVATaxWithHolding(VAWithHolding.computeWithHolding(this, grossPay));
        //System.out.print("VA Tax Withholding: " + pc.getVATaxWithHolding());
        double t = grossPay.getCost() - (pc.getFederalTaxWithHolding().getCost() +
                                         pc.getSSWithHolding().getCost() +
                                         pc.getMedicareWithHolding().getCost() +
                                         pc.getVATaxWithHolding().getCost());
        pc.setNetPay(new Dollar("" + t));
        payCheckList.add(pc);
    }

    public String getName() { return fn + " " + ln; }

    public String getSsn() { return ssn; }

    public List<PayCheck> getPayCheckList() { return payCheckList; }

    public String toString() {
        return  fn + " " + ln + " " + ssn + "\n" +
                street + " " + city + ", " + state + "\n" +
                "Hourly Rate: $" + hourlyRate + "\n" +
                "W4: " + w4 + "\n" +
                "VA W4: " + vaw4 + "\n";
    }
}
