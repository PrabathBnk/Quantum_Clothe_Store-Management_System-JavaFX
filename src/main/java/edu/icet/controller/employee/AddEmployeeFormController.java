package edu.icet.controller.employee;

import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.EmployeeDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.SuperService;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeFormController implements Initializable {

    private EmployeeService service;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {
        boolean isEmployeeAdded = service.addEmployee(new EmployeeDto(
                lblEmployeeId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtContact.getText()
        ));

        if (isEmployeeAdded) {
            new Alert(Alert.AlertType.INFORMATION, "Employee Added Successfully!").showAndWait();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service = ServiceFactory.getInstance().getService(ServiceType.EMPLOYEE);
        lblEmployeeId.setText(service.generateEmployeeId());
    }
}
