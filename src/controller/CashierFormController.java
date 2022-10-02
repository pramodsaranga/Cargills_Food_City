package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class CashierFormController {
    public AnchorPane CashierHomeContext;
    public AnchorPane CashierLoadContext;

    public void BackToDashOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierHomeContext.getChildren().clear();
        CashierHomeContext.getChildren().add(load);
    }

    public void ManageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierLoadContext.getChildren().clear();
        CashierLoadContext.getChildren().add(load);
    }

    public void ManageOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierLoadContext.getChildren().clear();
        CashierLoadContext.getChildren().add(load);
    }
}
