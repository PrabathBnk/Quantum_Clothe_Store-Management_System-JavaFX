package edu.icet.controller.user;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.dto.EmployeeDto;
import edu.icet.dto.UserDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.SuperService;
import edu.icet.service.custom.EmployeeService;
import edu.icet.service.custom.UserService;
import edu.icet.util.SceneSwitcher;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserFormController implements Initializable {

    @FXML
    private Label lblEmailAddress;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblUserId;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private JFXComboBox<String> cmbUserType;

    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        UserService service = ServiceFactory.getInstance().getService(ServiceType.USER);
        boolean isUserAdded = service.addNewUser(new UserDto(
                lblEmployeeId.getText(),
                lblUserId.getText(),
                cmbUserType.getValue(),
                null
        ));

        if (isUserAdded) {
            new Alert(Alert.AlertType.INFORMATION, "User Added Successfully").showAndWait();
            Node node = (Node) event.getTarget();
            Stage stage = (Stage) node.getScene().getWindow();
            UserFormController ufc = (UserFormController) stage.getUserData();
            ufc.loadUserTable();
            ufc.getTableView().getSelectionModel().clearSelection();
            stage.close();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        EmployeeService employeeService = ServiceFactory.getInstance().getService(ServiceType.EMPLOYEE);
        EmployeeDto employeeDto = employeeService.getEmployeeIfNonUser(txtSearchBar.getText());
        if (null!=employeeDto){
            lblEmployeeId.setText(employeeDto.getEmployeeId());
            lblName.setText(employeeDto.getName());
            lblEmailAddress.setText(employeeDto.getEmailAddress());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserService service = ServiceFactory.getInstance().getService(ServiceType.USER);
        lblUserId.setText(service.generateUserId());

        ObservableList<String> userTypes = FXCollections.observableArrayList();
        userTypes.add("General");
        userTypes.add("Admin");

        cmbUserType.setItems(userTypes);
        cmbUserType.setValue("General");
    }
}
