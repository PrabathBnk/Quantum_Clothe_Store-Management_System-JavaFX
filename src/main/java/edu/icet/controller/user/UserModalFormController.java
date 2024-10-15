package edu.icet.controller.user;

import edu.icet.dto.UserDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.service.custom.UserService;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserModalFormController implements Initializable {

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblEmailAddress;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        UserService service = ServiceFactory.getInstance().getService(ServiceType.USER);
        boolean isUserDeleted = service.deleteUser(UserFormController.userTableDto);

        if (isUserDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "User Deleted Successfully!").showAndWait();
            Node node = (Node) event.getTarget();
            Stage stage = (Stage) node.getScene().getWindow();
            UserFormController ufc = (UserFormController) stage.getUserData();
            ufc.loadUserTable();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUserId.setText(UserFormController.userTableDto.getUserID());
        lblName.setText(UserFormController.userTableDto.getName());
        lblEmailAddress.setText(UserFormController.userTableDto.getEmailAddress());
    }
}
