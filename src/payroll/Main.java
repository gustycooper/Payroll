package payroll;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("payroll.fxml"));
        primaryStage.setTitle("Pay Roll");
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.show();
    }


    public static EmployeeDB edb;

    public static void main(String[] args) {
        FederalWithHolding.createFederalWithHoldings();
        VAWithHolding.createVAWithHoldings();
        System.out.println("Starting Pay Roll");
        edb = new EmployeeDB("employees.txt");
        edb.computePayChecks("employeeTimeSheets.txt");
        double totalFederalWithHolding = 0, totalSSWithHolding = 0, totalMedicareWithHolding = 0, totalVAWithHolding = 0;

        launch(args);
    }
}
