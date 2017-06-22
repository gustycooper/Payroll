package payroll;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {
        boolean debug = false;
        FederalWithHolding.createFederalWithHoldings();
        VAWithHolding.createVAWithHoldings();
        System.out.println("\n\n\n");
        EmployeeDB edb = new EmployeeDB("employees.txt");
        edb.computePayChecks("employeeTimeSheets.txt");
        double totalFederalWithHolding = 0, totalSSWithHolding = 0, totalMedicareWithHolding = 0, totalVAWithHolding = 0;

        for (Employee e : edb.getEmployees()) {
            System.out.println(e);
            for (PayCheck pc : e.getPayCheckList()) {
                System.out.println(pc);
                System.out.println();
                totalFederalWithHolding += pc.getFederalTaxWithHolding().getCost();
                totalSSWithHolding += pc.getSSWithHolding().getCost();
                totalMedicareWithHolding += pc.getMedicareWithHolding().getCost();
                totalVAWithHolding += pc.getVATaxWithHolding().getCost();
            }
        }
        System.out.println("Total Federal Withholding: " + new Dollar(totalFederalWithHolding));
        System.out.println("Total SS Withholding: " + new Dollar(totalSSWithHolding));
        System.out.println("Total Medicare Withholding: " + new Dollar(totalMedicareWithHolding));
        System.out.println("Total VA Withholding: " + new Dollar(totalVAWithHolding));

        Scanner in = new Scanner(System.in);
        boolean goon = true;
        while (goon) {
            System.out.println("\n\nOptions: 1 - View Employee, 2 - Enter Hours, 3 - Quit");
            System.out.print("Enter Selection: ");
            String selection = in.nextLine();
            if (selection.equals("3"))
                break;
            System.out.print("Enter Employee Name: ");
            String name = in.nextLine();
            Employee emp = edb.findEmployee(name);
            if (emp == null)
                System.out.println(name + " not found.");
            else {
                if (selection.equals("1")) {
                    System.out.println(emp);
                    for (PayCheck pc : emp.getPayCheckList()) {
                        System.out.println(pc);
                        System.out.println();
                    }
                }
                else {
                    System.out.print("Enter Hours Worked: ");
                    double hoursWorked = Double.parseDouble(in.nextLine());
                    System.out.print("Enter Paydate: ");
                    String paydate = in.nextLine();
                    System.out.print("Enter Start Date: ");
                    String startdate = in.nextLine();
                    System.out.print("Enter End Date: ");
                    String enddate = in.nextLine();
                    emp.addPayCheck(hoursWorked, paydate, startdate, enddate);
                    try (
                            FileWriter fw = new FileWriter("employeeTimeSheets.txt", true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter writer = new PrintWriter(bw)
                        )
                    {
                        writer.println(emp.getSsn() + "," + paydate + "," + startdate + "," + enddate + "," + hoursWorked);
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("file error!");
                        e.printStackTrace();
                    }

                }

            }
        }



        if (debug) {
            for (FederalWithHolding fh : FederalWithHolding.biWeeklySingle)
                System.out.println(fh);
            for (FederalWithHolding fh : FederalWithHolding.biWeeklyMarried)
                System.out.println(fh);

            W4 w4 = new W4(0, new Dollar("0"), "Single", false);
            VAW4 vaw4 = new VAW4(0, 0, new Dollar("0"), false);

            Employee gusty = new Employee("Gusty","Cooper", "123", "12277 Calvert Ct.", "King George", "VA", new Dollar("100.00"), w4, vaw4);

            Dollar biWeeklyNet = new Dollar("1000");
            Dollar d = FederalWithHolding.computeWithHolding(gusty, biWeeklyNet);
            System.out.println("Single, $1000, Federal: " + d);
            d = SSWithHolding.computeWithHolding(gusty, biWeeklyNet);
            System.out.println("Single, $1000, SS: " + d);
            d = MedicareWithHolding.computeWithHolding(gusty, biWeeklyNet);
            System.out.println("Single, $1000, Medicare: " + d);
            d = VAWithHolding.computeWithHolding(gusty, biWeeklyNet);
            System.out.println("Single, $1000, VA: " + d);

            d = FederalWithHolding.computeWithHolding(gusty, new Dollar("3000"));
            System.out.println("Single, $3000: " + d);

            gusty.addPayCheck(10, "6/9/2017", "5/26/2017", "6/9/2017");
            System.out.println("\n" + gusty.getName() + "'s Pay Check");
            for (PayCheck pc : gusty.getPayCheckList()) {
                System.out.println(pc);
            }
        }

    }
}
