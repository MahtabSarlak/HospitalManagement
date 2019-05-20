package controller;
/**
 * Hospital managment project
 * @ author mahtab sarlak
 * admin username=mahtab admin password=1999
 * doctor username=96******
 * nurse username=95******
 * patient username=94******
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ConnectionUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main extends Application {
    private static Stage ps;

    public static Stage getPrimaryStage() {
        return ps;
    }

    Connection connection = ConnectionUtil.connectdb();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ps = primaryStage;
        ps.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
        ps.setTitle("Hospital");
        ps.setScene(new Scene(root, 800, 600));
        ps.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
