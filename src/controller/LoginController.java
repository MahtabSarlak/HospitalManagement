package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private TextField usertext;
    @FXML
    private PasswordField passtext;
    @FXML
    private Button loginbtn;


    private String type;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public Stage dialogStage;

    public void setType(String type) {
        this.type = type;
    }

    public LoginController() {
        connection = ConnectionUtil.connectdb();
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void handlebtn(ActionEvent actionEvent) throws IOException {
        String username = usertext.getText().toString();
        String password = (passtext.getText().toString());
        System.out.println(username);
        System.out.println(password);
        String sql = "select*from adminsignin where username=? and pass=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                infoBox("Enter Correct User or Password", "Failed", null);
            } else {
                infoBox("Login Successfull", "Success", null);
                if (!type.equals("finance")) {
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

                        dialogStage.close();
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Stage stage = Main.getPrimaryStage();
                        stage.setResizable(false);
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Main.class.getResource("../view/finance.fxml"));
                        AnchorPane finance = (AnchorPane) loader.load();
                        Scene scene = new Scene(finance);
                        stage.setScene(scene);

                        // Give the controller access to the main app.
                        FinanceController controller = loader.getController();

                        dialogStage.close();
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void infoBox(String infoMassage, String titleBar, String headerMassage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMassage);
        alert.setContentText(infoMassage);
        alert.showAndWait();
    }
}
