package edu.icet.controller.user;

import edu.icet.dto.UserTableDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.SuperService;
import edu.icet.service.custom.EmployeeService;
import edu.icet.service.custom.UserService;
import edu.icet.util.SceneSwitcher;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {

    static UserTableDto userTableDto;

    @FXML
    private BorderPane mainPane;

    @FXML
    private TableColumn<?, ?> colEmailAddress;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNum;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableView<UserTableDto> tblUser;

    @FXML
    void btnSlideBarEmployee(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.EMPLOYEE);
    }

    @FXML
    void btnSlideBarOrder(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.ORDER);
    }

    @FXML
    void btnSlideBarProduct(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.PRODUCT);
    }

    @FXML
    void btnSlideBarReport(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.REPORT);
    }

    @FXML
    void btnSlideBarSupplier(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.SUPPLIER);
    }

    @FXML
    public void btnSlideBarDashboard(ActionEvent actionEvent) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.ADMIN_DASHBOARD);
    }


    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/add_user_form.fxml");
        stage.setUserData(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmailAddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));

        loadUserTable();

        tblUser.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            userTableDto = newValue;

            Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/user_modal_form.fxml");
            stage.setUserData(this);
        });
    }

    void loadUserTable() {
        UserService service = ServiceFactory.getInstance().getService(ServiceType.USER);
        tblUser.setItems(FXCollections.observableArrayList(service.getAllUsers()));
    }

    TableView<UserTableDto> getTableView() {
        return tblUser;
    }
}
