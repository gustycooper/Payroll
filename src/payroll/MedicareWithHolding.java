package payroll;

public class MedicareWithHolding {
    public static Dollar computeWithHolding(Employee e, Dollar biWeeklyNet) {
        final double medicareRate = .0145;
        double d = biWeeklyNet.getCost() * medicareRate;
        return new Dollar("" + d);
    }
}
