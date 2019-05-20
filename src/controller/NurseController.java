package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ConnectionUtil;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NurseController {
    @FXML
    private TableView<Patientinfo> personTable;
    @FXML
    private TableColumn<Patientinfo, String> firstNameColumn;
    @FXML
    private TableColumn<Patientinfo, String> lastNameColumn;
    @FXML
    private TableColumn<Patientinfo, Integer> usernameColumn;
    @FXML
    private TableColumn<Patientinfo, String> nationalityColumn;
    @FXML
    private TableColumn<Patientinfo, Integer> phoneColumn;
    @FXML
    private TableColumn<Patientinfo, Integer> idColumn;
    @FXML
    private TableColumn<Patientinfo, Integer> doctorColumn;
    @FXML
    private TableColumn<Patientinfo, Integer> nurseColumn;
    @FXML
    private Label feeLabel;
    @FXML
    private TextArea descriptionTextArea;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private int username;

    public NurseController() {
        connection = ConnectionUtil.connectdb();
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the six columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty().asObject());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty().asObject());
        idColumn.setCellValueFactory(cellData -> cellData.getValue().verificationcodeProperty().asObject());
        nationalityColumn.setCellValueFactory(cellData -> cellData.getValue().nationalityProperty());
        doctorColumn.setCellValueFactory(cellData -> cellData.getValue().doctorProperty().asObject());
        nurseColumn.setCellValueFactory(cellData -> cellData.getValue().nurseProperty().asObject());

        // Clear person details.
        showPatientDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPatientDetails(newValue));
    }

    private void showPatientDetails(Patientinfo patientinfo) {
        if (patientinfo != null) {
            descriptionTextArea.setText(patientinfo.getDescribtion());
            feeLabel.setText(Integer.toString(patientinfo.getFee()));
        } else {
            descriptionTextArea.setText("");
            feeLabel.setText("");
        }
    }

    public void setTable(int username) {
        this.username = username;
        personTable.setItems(AdminDoctorController.getPatientinfoData());
    }

    public void handleExit(ActionEvent actionEvent) throws IOException {
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }


    public void handleDelete(ActionEvent actionEvent) {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();


        if (selectedIndex >= 0) {
            if (personTable.getSelectionModel().getSelectedItem().getNurse() == username) {
                Finance finance = new Finance();
                finance.getPatientFee(personTable.getSelectionModel().getSelectedItem().getFee());
                personTable.getItems().remove(selectedIndex);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(Main.getPrimaryStage());
                alert.setTitle("Access Denied");
                alert.setContentText("Please select your patient.");
                alert.showAndWait();
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(Main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    public void handleBill(ActionEvent actionEvent) {
        Patientinfo selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            try {
                // Load the fxml file and create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("../view/bill.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Hospital Bill");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(Main.getPrimaryStage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                BillController controller = loader.getController();
                controller.setPatientinfo(selectedPerson);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(Main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }

    }
}




