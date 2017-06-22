package payroll;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Dollar {
    private BigDecimal dollar;

    public Dollar(String dollar) { this.dollar = new BigDecimal(dollar); }

    public Dollar(double dollar) { this.dollar = new BigDecimal(dollar); }

    //public String getCost() { return dollar.setScale(2, RoundingMode.HALF_EVEN).toString(); }

    public double getCost() { return dollar.doubleValue(); }

    public void setCost(String dollar) { this.dollar = new BigDecimal(dollar); }

    public String toString(){ return dollar.setScale(2, RoundingMode.HALF_EVEN).toString(); }

    public boolean equals(Dollar c) {
        return dollar.setScale(2, RoundingMode.HALF_EVEN).toString().equals(c.getCost());
    }
}
