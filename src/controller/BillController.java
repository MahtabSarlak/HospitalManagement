package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BillController {
    @FXML
    private Label doctorLabel ;
    @FXML
    private Label totalLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label descriptionLabel;


    private Patientinfo patientinfo;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }
    /**
     * Sets the person to be edited in the dialog.
     *
     * @param patientinfo
     */
    public void setPatientinfo(Patientinfo patientinfo) {

        this.patientinfo = patientinfo;
        nameLabel.setText(patientinfo.getFirstname());
        lastNameLabel.setText(patientinfo.getLastname());
        totalLabel.setText(Integer.toString(patientinfo.getFee()));
        descriptionLabel.setText(patientinfo.getDescribtion());
        String doctor="";
        for (Doctorinfo d:HomeController.getDoctorinfoData()) {
            if(d.getUsername()==patientinfo.getDoctor())
            {
                doctor=d.getFirstname()+" "+d.getLastname();
            }
        }
        doctorLabel.setText(doctor);
    }

}
