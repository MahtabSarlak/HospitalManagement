package controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientEditDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField verificationCodeField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField nationalityField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField doctorField;
    @FXML
    private TextField nurseField;

    private Stage dialogStage;
    private Patientinfo patientinfo;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param patientinfo
     */
    public void setPatientinfo(Patientinfo patientinfo) {
        this.patientinfo = patientinfo;

        firstNameField.setText(patientinfo.getFirstname());
        lastNameField.setText(patientinfo.getLastname());
        userNameField.setText(Integer.toString(patientinfo.getUsername()));
        addressField.setText(patientinfo.getAddress());
        phoneField.setText(Integer.toString(patientinfo.getPhone()));
        nationalityField.setText(patientinfo.getNationality());
        genderField.setText(patientinfo.getGender());
        verificationCodeField.setText(Integer.toString(patientinfo.getVerificationcode()));
        doctorField.setText(Integer.toString(patientinfo.getDoctor()));
        nurseField.setText(Integer.toString(patientinfo.getNurse()));


    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            patientinfo.setFirstname(firstNameField.getText());
            patientinfo.setLastname(lastNameField.getText());
            patientinfo.setUsername(Integer.parseInt(userNameField.getText()));
            patientinfo.setAddress(addressField.getText());
            patientinfo.setPhone(Integer.parseInt(phoneField.getText()));
            patientinfo.setNationality(nationalityField.getText());
            patientinfo.setGender(genderField.getText());
            patientinfo.setVerificationcode(Integer.parseInt(verificationCodeField.getText()));
            patientinfo.setDoctor(Integer.parseInt(doctorField.getText()));
            patientinfo.setNurse(Integer.parseInt(nurseField.getText()));
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if ((firstNameField.getText() == null || firstNameField.getText().length() == 0)) {
            errorMessage += "No valid first name!\n";
        } else {
            if (firstNameField.getText().matches("[A-Z a-z]+") == false) {
                errorMessage += "No proper first name!\n";
            }
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        } else {
            if (lastNameField.getText().matches("[A-Z a-z]+") == false) {
                errorMessage += "No proper last name!\n";
            }
        }
        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "No valid address!\n";
        } else {
            if (addressField.getText().matches("[A-Z a-z]+") == false) {
                errorMessage += "No proper address!\n";
            }
        }
        if (verificationCodeField.getText() == null || verificationCodeField.getText().length() == 0) {
            errorMessage += "No valid verification code!\n";
        } else {
            if (verificationCodeField.getText().matches("[0-9]+")==false) {
                errorMessage += "No proper verification code!\n";
            }
        }

        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "No valid phone!\n";
        } else {
            if (phoneField.getText().matches("[0-9]+")==false) {
                errorMessage += "No proper phone!\n";
            }
        }
        if (nationalityField.getText() == null || nationalityField.getText().length() == 0) {
            errorMessage += "No valid nationality!\n";
        } else {
            if (nationalityField.getText().matches("[A-Z a-z]+") == false) {
                errorMessage += "No proper nationality!\n";
            }
        }
        if (genderField.getText() == null || genderField.getText().length() == 0) {
            errorMessage += "No valid gender!\n";
        } else {
            if (genderField.getText().matches("[A-Z a-z]+") == false) {
                errorMessage += "No proper gender!\n";
            }
        }
        if (userNameField.getText() == null || userNameField.getText().length() == 0) {
            errorMessage += "No valid userName!\n";
        } else {
            if (userNameField.getText().matches("94[0-9]+")==false) {
                errorMessage += "No proper userName!\n";
            }
        }
        if (doctorField.getText() == null || doctorField.getText().length() == 0) {
            errorMessage += "No valid doctor userName!\n";
        } else if (doctorField.getText().matches("96[0-9]+")==false) {
            errorMessage += "No proper doctor userName!\n";
        } else {
            for (Doctorinfo d : HomeController.getDoctorinfoData()) {
                if (d.getUsername() == Integer.parseInt(doctorField.getText())) {
                    if (d.getPatients().size() >= 3) {
                        errorMessage += "Doctor has another appointment.Select another doctor.";
                    }
                }
            }
        }
        if (nurseField.getText() == null || nurseField.getText().length() == 0) {
            errorMessage += "No valid nurse userName!\n";
        } else if (nurseField.getText().matches("95[0-9]+")==false) {
            errorMessage += "No proper nurse userName!\n";
        } else {
            for (Nurseinfo d : AdminDoctorController.getNurseinfoData()) {
                if (d.getUsername() == Integer.parseInt(nurseField.getText())) {
                    if (d.getPatients().size() >= 5) {
                        errorMessage += "Nurse has another appointment.Select another nurse.";
                    }
                }
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
