package edu.icet.controller.employee;


import edu.icet.dto.EmployeeDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.LoadFontUtil;
import edu.icet.util.SceneSwitcher;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class EmployeeFormController {

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
    private TableView<String> tblEmployee;

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
        SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/add_employee_form.fxml");
    }
}
