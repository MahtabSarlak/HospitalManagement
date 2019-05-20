package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ConnectionUtil;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDoctorController {
    @FXML
    private TableView<Doctorinfo> personTable;
    @FXML
    private TableColumn<Doctorinfo, String> firstNameColumn;
    @FXML
    private TableColumn<Doctorinfo, String> lastNameColumn;

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

    private HomeController home;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public AdminDoctorController() {
        connection = ConnectionUtil.connectdb();
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());

        // Clear person details.
        showDoctorDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDoctorDetails(newValue));
    }

    public void setHome(HomeController home) {
        this.home = home;
        // Add observable list data to the table
        personTable.setItems(home.getDoctorinfoData());
    }

    public void setTable() {
        personTable.setItems(HomeController.getDoctorinfoData());
    }

    private static ObservableList<Nurseinfo> nurseinfoData = FXCollections.observableArrayList();

    /**
     * Returns the data as an observable list of Persons.
     *
     * @return
     */
    public static ObservableList<Nurseinfo> getNurseinfoData() {
        return nurseinfoData;
    }

    private static ObservableList<Patientinfo> patientinfoData = FXCollections.observableArrayList();

    /**
     * Returns the data as an observable list of Persons.
     *
     * @return
     */
    public static ObservableList<Patientinfo> getPatientinfoData() {
        return patientinfoData;
    }

    public void showDoctorDetails(Doctorinfo doctorinfo) {
        if (doctorinfo != null) {
            firstNameLabel.setText(doctorinfo.getFirstname());
            lastNameLabel.setText(doctorinfo.getLastname());
            userNameLabel.setText(Integer.toString(doctorinfo.getUsername()));
            addressLabel.setText(doctorinfo.getAddress());
            phoneLabel.setText(Integer.toString(doctorinfo.getPhone()));
            verificationCodeLabel.setText(Integer.toString(doctorinfo.getVerificationcode()));
            nationalityLabel.setText(doctorinfo.getNationality());
            genderLabel.setText(doctorinfo.getGender());
            qualificationLabel.setText(doctorinfo.getQualification());
            specialistLabel.setText(doctorinfo.getSpecialist());
            salaryLabel.setText(Integer.toString(doctorinfo.getSalary()));
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

    public void handleDoctorMenu(ActionEvent actionEvent) {
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

    public boolean showPersonEditDialog(Doctorinfo doctorinfo) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/doctorEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            DoctorEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDoctorinfo(doctorinfo);

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
        Doctorinfo tempDoctor = new Doctorinfo();
        boolean okClicked = showPersonEditDialog(tempDoctor);
        if (okClicked) {
            home.getDoctorinfoData().add(tempDoctor);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEdit() {
        Doctorinfo d = personTable.getSelectionModel().getSelectedItem();
        if (d != null) {
            boolean okClicked = showPersonEditDialog(d);
            if (okClicked) {
                showDoctorDetails(d);
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

    /**
     * Saves the file to the person file that is currently open.
     */
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
