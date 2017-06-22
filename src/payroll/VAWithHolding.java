package payroll;

/*
 * See https://www.tax.virginia.gov/withholding-calculator for algorithm
 */

import java.util.ArrayList;
import java.util.List;

public class VAWithHolding {
    Dollar lowerLimit;
    Dollar upperLimit;
    Dollar baseWithHolding;
    double percentage;

    public static List<VAWithHolding> withHoldings;

    public VAWithHolding(Dollar ll, Dollar ul, Dollar base, double percentage) {
        lowerLimit = ll;
        upperLimit = ul;
        baseWithHolding = base;
        this.percentage = percentage;
    }

    public static void createVAWithHoldings() { // TODO - update to use a file
        withHoldings = new ArrayList<>();
        withHoldings.add(new VAWithHolding(new Dollar("0"), new Dollar("3000"), new Dollar("0"), .02));
        withHoldings.add(new VAWithHolding(new Dollar("3000"), new Dollar("5000"), new Dollar("60"), .03));
        withHoldings.add(new VAWithHolding(new Dollar("5000"), new Dollar("17000"), new Dollar("120"), .05));
        withHoldings.add(new VAWithHolding(new Dollar("17000"), new Dollar("700000"), new Dollar("720"), .0575));
    }

    public static Dollar computeWithHolding(Employee e, Dollar biWeeklyGross) {
        final int payPeriods = 26;
        if (e.getVAW4().exempt())
            return new Dollar("0");

        double t = biWeeklyGross.getCost() * payPeriods - // 26 pay periods
                   (3000 +
                    e.getVAW4().getPersonalExemptions()*930.00 +
                    e.getVAW4().getBlindExemptions()*800);
        for (VAWithHolding vh : withHoldings) {
            if (t > vh.lowerLimit.getCost() && t <= vh.upperLimit.getCost()) {
                double wh = vh.baseWithHolding.getCost() + vh.percentage * (t - vh.lowerLimit.getCost());
                wh = wh/payPeriods;
                return new Dollar("" + wh);
            }
        }
        return new Dollar("0");
    }
}
