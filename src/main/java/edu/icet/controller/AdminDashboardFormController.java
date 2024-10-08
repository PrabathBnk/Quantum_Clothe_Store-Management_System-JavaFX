package edu.icet.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AdminDashboardFormController {
    public BorderPane mainBorderPane;


    public void btnTabOnAction(ActionEvent actionEvent) {
        try {
            mainBorderPane.getScene().setRoot(FXMLLoader.load(getClass().getResource("../../../view/employee_form.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
