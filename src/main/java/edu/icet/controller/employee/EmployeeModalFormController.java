package edu.icet.controller.employee;

import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.EmployeeDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeModalFormController implements Initializable {

    @FXML
    private Label lblEmployeeId;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm to delete!");
        alert.showAndWait();
        if (alert.getResult().getButtonData().toString().equals("OK_DONE")) {
            EmployeeService service = ServiceFactory.getInstance().getService(ServiceType.EMPLOYEE);
            boolean isUserDeleted = service.deleteEmployee(EmployeeFormController.getEmployee());

            if (isUserDeleted){
                new Alert(Alert.AlertType.INFORMATION, "User deleted successfully!");
                refreshEmployeeTable(event);
                getStage(event).close();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        EmployeeService service = ServiceFactory.getInstance().getService(ServiceType.EMPLOYEE);
        boolean isEmployeeUpdated = service.updateEmployee(new EmployeeDto(
                lblEmployeeId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtContact.getText()
        ));

        if (isEmployeeUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully!").showAndWait();
            refreshEmployeeTable(event);
            getStage(event).close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblEmployeeId.setText(EmployeeFormController.getEmployee().getEmployeeId());
        txtName.setText(EmployeeFormController.getEmployee().getName());
        txtContact.setText(EmployeeFormController.getEmployee().getContact());
        txtEmail.setText(EmployeeFormController.getEmployee().getEmailAddress());
    }

    private Stage getStage(Event event) {
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }

    private void refreshEmployeeTable(Event event){
        EmployeeFormController efc = (EmployeeFormController) getStage(event).getUserData();
        efc.getEmployeeTable().getSelectionModel().clearSelection();
        efc.loadEmployeeTable();
    }
}
