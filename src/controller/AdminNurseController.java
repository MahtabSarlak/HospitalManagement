package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ConnectionUtil;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminNurseController {
    @FXML
    private TableView<Nurseinfo> personTable;
    @FXML
    private TableColumn<Nurseinfo, String> firstNameColumn;
    @FXML
    private TableColumn<Nurseinfo, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label verificationCodeLabel;
    @FXML
    private Label nationalityLabel;
    @FXML

    private Label genderLabel;
    @FXML
    private Label qualificationLabel;
    @FXML
    private Label specialistLabel;
    @FXML
    private Label salaryLabel;

    private AdminDoctorController home;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public AdminNurseController() {
        connection = ConnectionUtil.connectdb();
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());

        // Clear person details.
        showNurseDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showNurseDetails(newValue));
    }

    public void setTable() {
        personTable.setItems(AdminDoctorController.getNurseinfoData());
    }

    public void showNurseDetails(Nurseinfo nurseinfo) {
        if (nurseinfo != null) {
            firstNameLabel.setText(nurseinfo.getFirstname());
            lastNameLabel.setText(nurseinfo.getLastname());
            userNameLabel.setText(Integer.toString(nurseinfo.getUsername()));
            addressLabel.setText(nurseinfo.getAddress());
            phoneLabel.setText(Integer.toString(nurseinfo.getPhone()));
            verificationCodeLabel.setText(Integer.toString(nurseinfo.getVerificationcode()));
            nationalityLabel.setText(nurseinfo.getNationality());
            genderLabel.setText(nurseinfo.getGender());
            qualificationLabel.setText(nurseinfo.getQualification());
            specialistLabel.setText(nurseinfo.getSpecialist());
            salaryLabel.setText(Integer.toString(nurseinfo.getSalary()));
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            userNameLabel.setText("");
            addressLabel.setText("");
            phoneLabel.setText("");
            verificationCodeLabel.setText("");
            nationalityLabel.setText("");
            genderLabel.setText("");
            qualificationLabel.setText("");
            specialistLabel.setText("");
            salaryLabel.setText("");
        }
    }

    public void handleDoctorMenu(ActionEvent actionEvent) throws Exception {
        try {
            Stage stage = Main.getPrimaryStage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/admindoctor.fxml"));
            BorderPane admindoctor = (BorderPane) loader.load();
            Scene scene = new Scene(admindoctor);
            stage.setScene(scene);
            // Give the controller access to the main app.
            AdminDoctorController controller = loader.getController();
            controller.setTable();
            stage.show();
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public void handleNurseMenu(ActionEvent actionEvent) {
    }

    public void handlePatientMenu(ActionEvent actionEvent) throws Exception {
        try {
            Stage stage = Main.getPrimaryStage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/adminpatient.fxml"));
            BorderPane adminpatient = (BorderPane) loader.load();
            Scene scene = new Scene(adminpatient);
            stage.setScene(scene);
            // Give the controller access to the main app.
            AdminPatientController controller = loader.getController();
            controller.setTable();
            stage.show();

        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public boolean showNurseEditDialog(Nurseinfo nurseinfo) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/NurseEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            NurseEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setNurseinfo(nurseinfo);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleNew() {
        Nurseinfo tempNurse = new Nurseinfo();
        boolean okClicked = showNurseEditDialog(tempNurse);
        if (okClicked) {
            home.getNurseinfoData().add(tempNurse);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEdit() {
        Nurseinfo selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = showNurseEditDialog(selectedPerson);
            if (okClicked) {
                showNurseDetails(selectedPerson);
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

    public void handleDelete(ActionEvent actionEvent) {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
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

    @FXML
    private void handleSave() {

    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() throws IOException {
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}