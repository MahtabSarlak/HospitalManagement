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

public class AdminPatientController {
    @FXML
    private TableView<Patientinfo> personTable;
    @FXML
    private TableColumn<Patientinfo, String> firstNameColumn;
    @FXML
    private TableColumn<Patientinfo, String> lastNameColumn;

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
    private Label doctorLabel;
    @FXML
    private Label nurseLabel;


    private AdminDoctorController home;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public AdminPatientController() {
        connection = ConnectionUtil.connectdb();
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());

        // Clear person details.
        showPatientDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPatientDetails(newValue));
    }

    public void setTable() {
        personTable.setItems(AdminDoctorController.getPatientinfoData());
    }

    public void showPatientDetails(Patientinfo patientinfo) {
        if (patientinfo != null) {
            firstNameLabel.setText(patientinfo.getFirstname());
            lastNameLabel.setText(patientinfo.getLastname());
            userNameLabel.setText(Integer.toString(patientinfo.getUsername()));
            addressLabel.setText(patientinfo.getAddress());
            phoneLabel.setText(Integer.toString(patientinfo.getPhone()));
            verificationCodeLabel.setText(Integer.toString(patientinfo.getVerificationcode()));
            nationalityLabel.setText(patientinfo.getNationality());
            genderLabel.setText(patientinfo.getGender());
            doctorLabel.setText(Integer.toString(patientinfo.getDoctor()));
            nurseLabel.setText(Integer.toString(patientinfo.getNurse()));


        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            userNameLabel.setText("");
            addressLabel.setText("");
            phoneLabel.setText("");
            verificationCodeLabel.setText("");
            nationalityLabel.setText("");
            genderLabel.setText("");
            doctorLabel.setText("");
            nurseLabel.setText("");

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

    public void handleNurseMenu(ActionEvent actionEvent) throws Exception {
        try {
            Stage stage = Main.getPrimaryStage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/adminnurse.fxml"));
            BorderPane adminnurse = (BorderPane) loader.load();
            Scene scene = new Scene(adminnurse);
            stage.setScene(scene);
            // Give the controller access to the main app.
            AdminNurseController controller = loader.getController();
            controller.setTable();
            stage.show();
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public void handlePatientMenu(ActionEvent actionEvent) {
    }

    public boolean showPatientEditDialog(Patientinfo patientinfo) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/PatientEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PatientEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPatientinfo(patientinfo);

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
        Patientinfo tempPatient = new Patientinfo();
        boolean okClicked = showPatientEditDialog(tempPatient);
        if (okClicked) {
            home.getPatientinfoData().add(tempPatient);
            for (Doctorinfo d : HomeController.getDoctorinfoData()) {
                if (d.getUsername() == tempPatient.getDoctor()) {
                    d.getPatients().add(tempPatient);
                }
            }
            for (Nurseinfo d : AdminDoctorController.getNurseinfoData()) {
                if (d.getUsername() == tempPatient.getNurse()) {
                    d.getPatients().add(tempPatient);
                }
            }
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEdit() {
        Patientinfo selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            Patientinfo temp = selectedPerson;
            boolean okClicked = showPatientEditDialog(selectedPerson);
            if (okClicked) {
                for (Doctorinfo d : HomeController.getDoctorinfoData()) {
                    if (d.getUsername() == selectedPerson.getDoctor() && d.getUsername() == temp.getDoctor()) {
                        d.getPatients().remove(temp);
                        d.getPatients().add(selectedPerson);
                    }
                    if (d.getUsername() == selectedPerson.getDoctor() && !(d.getUsername() == temp.getDoctor())) {
                        d.getPatients().add(selectedPerson);
                    }
                }
                for (Nurseinfo d : AdminDoctorController.getNurseinfoData()) {
                    if (d.getUsername() == selectedPerson.getNurse() && d.getUsername() == temp.getNurse()) {
                        d.getPatients().remove(temp);
                        d.getPatients().add(selectedPerson);
                    }
                    if (d.getUsername() == selectedPerson.getNurse() && !(d.getUsername() == temp.getNurse())) {
                        d.getPatients().add(selectedPerson);
                    }
                }
                showPatientDetails(selectedPerson);
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
        Patientinfo selected = personTable.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0) {
            for (Doctorinfo d : HomeController.getDoctorinfoData()) {
                if (d.getUsername() == selected.getDoctor()) {
                    d.getPatients().remove(selected);
                }
            }
            for (Nurseinfo d : AdminDoctorController.getNurseinfoData()) {
                if (d.getUsername() == selected.getNurse()) {
                    d.getPatients().remove(selected);
                }
            }
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
    private void handleExit() throws Exception {
        //  System.exit(0);
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}