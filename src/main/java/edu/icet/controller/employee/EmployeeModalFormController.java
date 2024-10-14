package edu.icet.controller.employee;

import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.EmployeeDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.ServiceType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
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
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            EmployeeFormController efc = (EmployeeFormController) stage.getUserData();
            efc.getEmployeeTable().getSelectionModel().clearSelection();
            efc.loadEmployeeTable();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblEmployeeId.setText(EmployeeFormController.getEmployee().getEmployeeId());
        txtName.setText(EmployeeFormController.getEmployee().getName());
        txtContact.setText(EmployeeFormController.getEmployee().getContact());
        txtEmail.setText(EmployeeFormController.getEmployee().getEmailAddress());
    }
}
