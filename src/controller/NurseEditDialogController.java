package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NurseEditDialogController {
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
    private TextField qualificationField;
    @FXML
    private TextField specialistField;
    @FXML
    private TextField salaryField;


    private Stage dialogStage;
    private Nurseinfo nurseinfo;
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
     * @param nurseinfo
     */
    public void setNurseinfo(Nurseinfo nurseinfo) {
        this.nurseinfo = nurseinfo;

        firstNameField.setText(nurseinfo.getFirstname());
        lastNameField.setText(nurseinfo.getLastname());
        userNameField.setText(Integer.toString(nurseinfo.getUsername()));
        addressField.setText(nurseinfo.getAddress());
        phoneField.setText(Integer.toString(nurseinfo.getPhone()));
        qualificationField.setText(nurseinfo.getQualification());
        nationalityField.setText(nurseinfo.getNationality());
        genderField.setText(nurseinfo.getGender());
        specialistField.setText(nurseinfo.getSpecialist());
        salaryField.setText(Integer.toString(nurseinfo.getSalary()));
        verificationCodeField.setText(Integer.toString(nurseinfo.getVerificationcode()));

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
            nurseinfo.setFirstname(firstNameField.getText());
            nurseinfo.setLastname(lastNameField.getText());
            nurseinfo.setUsername(Integer.parseInt(userNameField.getText()));
            nurseinfo.setAddress(addressField.getText());
            nurseinfo.setPhone(Integer.parseInt(phoneField.getText()));
            nurseinfo.setNationality(nationalityField.getText());
            nurseinfo.setGender(genderField.getText());
            nurseinfo.setSpecialist(specialistField.getText());
            nurseinfo.setSalary(Integer.parseInt(salaryField.getText()));
            nurseinfo.setVerificationcode(Integer.parseInt(verificationCodeField.getText()));
            nurseinfo.setQualification(qualificationField.getText());


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
        if (specialistField.getText() == null || specialistField.getText().length() == 0) {
            errorMessage += "No valid specialist!\n";
        } else {
            if (specialistField.getText().matches("[A-Z a-z]+") == false) {
                errorMessage += "No proper specialist!\n";
            }
        }
        if (qualificationField.getText() == null || qualificationField.getText().length() == 0) {
            errorMessage += "No valid qualification!\n";
        } else {
            if (qualificationField.getText().matches("[A-Z a-z]+") == false) {
                errorMessage += "No proper specialist!\n";
            }
        }
        if (salaryField.getText() == null || salaryField.getText().length() == 0) {
            errorMessage += "No valid salary!\n";
        } else {
            if (salaryField.getText().matches("[0-9]+")==false) {
                errorMessage += "No proper salary!\n";
            }
        }
        if (userNameField.getText() == null || userNameField.getText().length() == 0) {
            errorMessage += "No valid userName!\n";
        } else {
            if (userNameField.getText().matches("95[0-9]+")==false) {
                errorMessage += "No proper userName!\n";
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
