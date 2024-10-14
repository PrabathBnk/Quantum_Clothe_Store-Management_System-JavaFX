package edu.icet.controller.employee;


import edu.icet.dto.EmployeeDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.SceneSwitcher;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


public class EmployeeFormController implements Initializable {

    private static EmployeeDto employee;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNum;

    @FXML
    private TableView<EmployeeDto> tblEmployee;

    @FXML
    private BorderPane mainPane;


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
    void btnSlideBarUser(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.USER);
    }

    public void btnSlideBarDashboard(ActionEvent actionEvent) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.ADMIN_DASHBOARD);
    }



    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {
        Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/add_employee_form.fxml");
        stage.setUserData(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));

        EmployeeService employeeService = ServiceFactory.getInstance().getService(ServiceType.EMPLOYEE);
        ObservableList<EmployeeDto> employeeObservableList = FXCollections.observableArrayList(employeeService.getAllEmployees());

        tblEmployee.setItems(employeeObservableList);

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            employee = newValue;
            Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/employee_modal_form.fxml");
            stage.setUserData(this);
        });
    }

    static EmployeeDto getEmployee(){
        return employee;
    }

    TableView<EmployeeDto> getEmployeeTable() {
        return tblEmployee;
    }

    void loadEmployeeTable() {
        EmployeeService employeeService = ServiceFactory.getInstance().getService(ServiceType.EMPLOYEE);
        ObservableList<EmployeeDto> employeeObservableList = FXCollections.observableArrayList(employeeService.getAllEmployees());

        tblEmployee.setItems(employeeObservableList);
    }

}
