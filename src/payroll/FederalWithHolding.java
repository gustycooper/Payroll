package payroll;

/*
 * Calculator to test algorithm: https://www.yourmoneypage.com/withhold/fedwh1.php
 * See file FederalTaxWithHoldingTables.txt for notes on the algorithm.  File contains IRS website with algorithm.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FederalWithHolding {
    Dollar lowerLimit;
    Dollar upperLimit;
    Dollar baseWithHolding;
    double percentage;

    public static List<FederalWithHolding> biWeeklySingle;
    public static List<FederalWithHolding> biWeeklyMarried;
    public static Dollar biWeeklyWithHoldingAllowance = new Dollar("155.80"); // TODO - get this from a file

    public static List<FederalWithHolding> readFile(String fileName) {
        List<FederalWithHolding> retList = null;
        if (fileName == null || fileName.equals(""))
            return retList;
        File file = new File(fileName);
        try {
            retList = new ArrayList<>();
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String regExp = "\\s*(\\s|,)\\s*";
                String[] pv = line.split(regExp);
                // System.out.println(Arrays.toString(pv));
                FederalWithHolding fh = new FederalWithHolding(new Dollar(pv[0]),new Dollar(pv[1]),new Dollar(pv[2]),Double.parseDouble(pv[3]));
                retList.add(fh);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        }
        return retList;
    }

    public static void createFederalWithHoldings() {
        biWeeklySingle = readFile("biWeeklySingle.txt");
        biWeeklyMarried = readFile("biWeeklyMarried.txt");
    }

    public static Dollar computeWithHolding(Employee e, Dollar biWeeklyGross) {
        double t = e.getW4().getTotalNumAllowances() * biWeeklyWithHoldingAllowance.getCost() +
                   e.getW4().getAdditionalWithHold().getCost();
        biWeeklyGross = new Dollar("" + (biWeeklyGross.getCost() - t));
        List<FederalWithHolding> l;
        if (e.getW4().exempt())
            return new Dollar("0");
        else if (e.getW4().getMaritalStatus().equals("Single")) {
            l = biWeeklySingle;
        }
        else {
            l = biWeeklyMarried;
        }
        for (FederalWithHolding fh : l) {
            if (biWeeklyGross.getCost() > fh.lowerLimit.getCost() && biWeeklyGross.getCost() <= fh.upperLimit.getCost()) {
                double wh = fh.baseWithHolding.getCost() + fh.percentage * (biWeeklyGross.getCost() - fh.lowerLimit.getCost());
                return new Dollar("" + wh);
            }
        }
        return new Dollar("0");
    }

    public FederalWithHolding(Dollar ll, Dollar ul, Dollar base, double percentage) {
        lowerLimit = ll;
        upperLimit = ul;
        baseWithHolding = base;
        this.percentage = percentage;

    }

    public String toString() {
        return lowerLimit + " " + upperLimit + " " + baseWithHolding + " " + percentage;
    }


}
