package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class FinanceController {

    @FXML
    private TableView<Employee> personTable;
    @FXML
    private TableColumn<Employee, Integer> usernameColumn;
    @FXML
    private TableColumn<Employee, Integer> salaryColumn;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label totalLabel;

    Finance finance=new Finance() ;
    private static ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    public FinanceController() {
        /*totalSalaryTextField.setText(Integer.toString(finance.getTotalSalary()));
        totalTextField.setText(Integer.toString(finance.getTotal()));*/
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the six columns.
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty().asObject());
        salaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());


    }
    public void setTable()
    {
         ObservableList<Employee> employeeData = FXCollections.observableArrayList();
         salaryLabel.setText(Integer.toString(finance.getSalary()));
        totalLabel.setText(Integer.toString(finance.getTotal()));
        for(Doctorinfo d:HomeController.getDoctorinfoData())
        {
            Employee e=new Employee();
            e.setUsername(d.getUsername());
            e.setSalary(d.getSalary());
            employeeData.add(e);
        }for(Nurseinfo d:AdminDoctorController.getNurseinfoData())
        {
            Employee e=new Employee();
            e.setUsername(d.getUsername());
            e.setSalary(d.getSalary());
            employeeData.add(e);
        }
        personTable.setItems(employeeData);
    }
    public void handleExit(ActionEvent actionEvent) throws IOException {
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void handlePay(ActionEvent actionEvent) {
        //finance.setSalary(0);
        finance.paySalary();
        totalLabel.setText(Integer.toString(finance.getTotal()));
        salaryLabel.setText(Integer.toString(finance.getSalary()));
    }
}
