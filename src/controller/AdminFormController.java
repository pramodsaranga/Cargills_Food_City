package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class AdminFormController {
    public AnchorPane AdminHomeContext;
    public AnchorPane AdminLoadContext;

    public void BackToOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminHomeContext.getChildren().clear();
        AdminHomeContext.getChildren().add(load);
    }

    public void ManageItemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageItemForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminLoadContext.getChildren().clear();
        AdminLoadContext.getChildren().add(load);
    }

    public void SystemReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SystemReportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminLoadContext.getChildren().clear();
        AdminLoadContext.getChildren().add(load);
    }
}
