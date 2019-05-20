package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NurseLoginController extends LoginController {
    @FXML
    private TextField usertext;
    @FXML
    private PasswordField passtext;

    private boolean access = false;

    public void handlebtn(ActionEvent actionEvent) throws IOException {
        int username = Integer.parseInt(usertext.getText());
        int password = Integer.parseInt(passtext.getText().toString());
        System.out.println(username);
        System.out.println(password);

        for (Nurseinfo n : AdminDoctorController.getNurseinfoData()) {
            if (n.getUsername() == username && n.getVerificationcode() == password) {
                access = true;
            }
        }

        if (access == false) {
            infoBox("Enter Correct User or Password", "Failed", null);
        } else {
            infoBox("Login Successfull", "Success", null);
            try {
                Stage stage = Main.getPrimaryStage();
                stage.setResizable(false);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("../view/nurse.fxml"));
                AnchorPane nurse = (AnchorPane) loader.load();
                Scene scene = new Scene(nurse);
                stage.setScene(scene);

                // Give the controller access to the main app.
                NurseController controller = loader.getController();
                controller.setTable(username);

                dialogStage.close();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
