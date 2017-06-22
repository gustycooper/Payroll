package payroll;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
 * Format of employees.txt
 * Three lines per employee
 * Line 1: employee information: fn,ln,ssn,street,city,state,hourlyRate
 * Line 2: W4 information: totalWithholdings,additionalWithholdingDollars,maritalStatus,exempt
 * Line 3: VAW4 information: personalExemptions,blindExemptions,additionalWithholdingDollars,exempt
 *
 * Format of pay hours.txt
 * One line per pay week
 * Line 1: pay information: payDate,startDate,endDate,hoursWorked
 */

public class EmployeeDB {
    List<Employee> employees;

    public EmployeeDB(String filename) {
        if (filename == null || filename.equals(""))
            filename = "employees.txt";
        File file = new File(filename);
        try {
            employees = new ArrayList<>();
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String line = read.nextLine(); // read employee line
                String regExp = "\\s*(\\s|,)\\s*";
                String[] empArray = line.split(",");
                // System.out.println(Arrays.toString(emp));
                line = read.nextLine(); // read W4 line
                String[] w4Array = line.split(",");
                line = read.nextLine(); // read VAW4 line
                String[] vaw4Array = line.split(",");
                W4 w4 = new W4(Integer.parseInt(w4Array[0]), new Dollar(w4Array[1]), w4Array[2], w4Array[3].equals("true") ? true : false);
                VAW4 vaw4 = new VAW4(Integer.parseInt(vaw4Array[0]), Integer.parseInt(vaw4Array[1]), new Dollar(vaw4Array[2]), vaw4Array[3].equals("true") ? true : false);
                Employee e = new Employee(empArray[0], empArray[1], empArray[2], empArray[3], empArray[4], empArray[5], new Dollar(empArray[6]), w4, vaw4);
                employees.add(e);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        }
    }

    public Employee findEmployee(String nameOrSsn) {
        for (Employee e : employees) {
            if (e.getSsn().equals(nameOrSsn) || e.getName().contains(nameOrSsn))
                return e;
        }
        return null;
    }

    public List<Employee> getEmployees() { return employees; }

    public void computePayChecks(String filename) { // TODO - add employee to file?
        File file = new File(filename);
        try {
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String line = read.nextLine(); // pay line
                String regExp = "\\s*(\\s|,)\\s*";
                String[] payArray = line.split(",");
                Employee emp = findEmployee(payArray[0]);
                if (emp != null)
                    emp.addPayCheck(Double.parseDouble(payArray[4]), payArray[1], payArray[2], payArray[3]);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        }
    }
}
