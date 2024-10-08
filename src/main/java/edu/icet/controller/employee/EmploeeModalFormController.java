package edu.icet.controller.employee;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EmploeeModalFormController {

    public BorderPane mainLayout;

    public void btnCloseOnClick(MouseEvent mouseEvent) {
        Stage stage = (Stage) mainLayout.getScene().getWindow();
        stage.close();
    }
}
