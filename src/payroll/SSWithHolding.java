package payroll;

public class SSWithHolding {
    public static Dollar computeWithHolding(Employee e, Dollar biWeeklyNet) {
        final double SSRate = .062;
        double d = biWeeklyNet.getCost() * SSRate;
        return new Dollar("" + d);
    }
}
