package edu.icet.controller.report;

import com.jfoenix.controls.JFXListView;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.ReportService;
import edu.icet.util.ReportType;
import edu.icet.util.SceneSwitcher;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportFormController implements Initializable {

    @FXML
    private BorderPane mainPane;


    @FXML
    private JFXListView<?> listEmployeeReport;

    @FXML
    private JFXListView<?> listInventoryReport;

    @FXML
    private JFXListView<?> listSalesReport;

    @FXML
    private JFXListView<?> listSupplierReport;

    private ReportService reportService;

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
    void btnSlideBarUser(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.USER);
    }

    public void btnSlideBarDashboard(ActionEvent actionEvent) {

    }


    @FXML
    void btnGenerateEmployeeReport(ActionEvent event) {
        if (reportService.generateReport(ReportType.EMPLOYEE_REPORT)) {
            new Alert(Alert.AlertType.INFORMATION, "Report generation successful!, But cannot bew shown.").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    @FXML
    void btnGenerateInventoryReport(ActionEvent event) {
        if (reportService.generateReport(ReportType.INVENTORY_REPORT)) {
            new Alert(Alert.AlertType.INFORMATION, "Report generation successful!, But cannot bew shown.").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    @FXML
    void btnGenerateSalesReport(ActionEvent event) {

    }

    @FXML
    void btnGenerateSupplierReport(ActionEvent event) {
        if (reportService.generateReport(ReportType.SUPPLIER_REPORT)) {
            new Alert(Alert.AlertType.INFORMATION, "Report generation successful!, But cannot bew shown.").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportService = ServiceFactory.getInstance().getService(ServiceType.REPORT);
    }
}
