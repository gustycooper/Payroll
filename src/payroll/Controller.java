package payroll;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Controller {

    @FXML
    private TextField employeeTextField;

    @FXML
    private Button viewButton;

    @FXML
    private TextField payDateTextField;

    @FXML
    private TextField startDate;

    @FXML
    private TextField endDate;

    @FXML
    private TextField hoursTextField;

    @FXML
    private Button enterButton;

    @FXML
    private TextArea payRollTextArea;

    @FXML
    void handleViewButton(ActionEvent event) {
        String name = employeeTextField.getText();
        Employee emp = Main.edb.findEmployee(name);
        payRollTextArea.clear();
        if (emp == null)
            payRollTextArea.appendText(name + " not found.");
        else {
            double totalFederalWithHolding = 0, totalSSWithHolding = 0, totalMedicareWithHolding = 0, totalVAWithHolding = 0;
            payRollTextArea.appendText(emp.toString());
            for (PayCheck pc : emp.getPayCheckList()) {
                payRollTextArea.appendText("------------------\n");
                payRollTextArea.appendText(pc.toString());
                payRollTextArea.appendText("\n");
                totalFederalWithHolding += pc.getFederalTaxWithHolding().getCost();
                totalSSWithHolding += pc.getSSWithHolding().getCost();
                totalMedicareWithHolding += pc.getMedicareWithHolding().getCost();
                totalVAWithHolding += pc.getVATaxWithHolding().getCost();
            }
            payRollTextArea.appendText("------------------\n");
            payRollTextArea.appendText("Total Federal Withholding: " + new Dollar(totalFederalWithHolding) + "\n");
            payRollTextArea.appendText("Total SS Withholding: " + new Dollar(totalSSWithHolding) + "\n");
            payRollTextArea.appendText("Total Medicare Withholding: " + new Dollar(totalMedicareWithHolding) + "\n");
            payRollTextArea.appendText("Total VA Withholding: " + new Dollar(totalVAWithHolding) +"\n");
        }

    }

    @FXML
    void handleEnterButton(ActionEvent event) {
        String name = employeeTextField.getText();
        Employee emp = Main.edb.findEmployee(name);
        payRollTextArea.clear();
        if (emp == null)
            payRollTextArea.appendText(name + " not found.");
        else {
            double hoursWorked = Double.parseDouble(hoursTextField.getText());
            String paydate = payDateTextField.getText();
            String startdate = startDate.getText();
            String enddate = endDate.getText();
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
                payRollTextArea.appendText("file error!");
                e.printStackTrace();
            }
        }
    }
}
