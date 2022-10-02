package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DashBoardFormController {
    public AnchorPane DashBoardContext;
    public TextField txtUsername;
    public PasswordField txtPassword;


    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUsername.getText().equalsIgnoreCase("Cashier") && txtPassword.getText().equals("1998")) {
            URL resource = getClass().getResource("../view/CashierForm.fxml");
            Parent load = FXMLLoader.load(resource);
            DashBoardContext.getChildren().clear();
            DashBoardContext.getChildren().add(load);
        } else if(txtUsername.getText().equalsIgnoreCase("Admin") && txtPassword.getText().equals("2021")){
            URL resource = getClass().getResource("../view/AdminForm.fxml");
            Parent load = FXMLLoader.load(resource);
            DashBoardContext.getChildren().clear();
            DashBoardContext.getChildren().add(load);
        }else{
        new Alert(Alert.AlertType.ERROR, "Invalid Username Or Password.Please Try Again" ).show();
      }
    }
    public void MovePassword(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void openHomeFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) DashBoardContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
