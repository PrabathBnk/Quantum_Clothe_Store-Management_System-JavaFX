package edu.icet.controller.report;

import com.jfoenix.controls.JFXListView;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.ReportService;
import edu.icet.service.custom.UserLogService;
import edu.icet.service.custom.UserService;
import edu.icet.util.ReportType;
import edu.icet.util.SceneSwitcher;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportFormController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private VBox sectionSalesReport;

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
    void btnSlideBarOrder(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.ORDER);
    }

    @FXML
    void btnSlideBarProduct(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.PRODUCT);
    }


    @FXML
    void btnSlideBarSupplier(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.SUPPLIER);
    }


    public void btnSlideBarDashboard(ActionEvent actionEvent) {
        UserService userService = ServiceFactory.getInstance().getService(ServiceType.USER);
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(),  userService.lastLogIsAdmin() ? SceneSwitcher.ADMIN_DASHBOARD: SceneSwitcher.GENERAL_DASHBOARD);
    }


    @FXML
    void btnGenerateEmployeeReport(ActionEvent event) {
        if (!reportService.generateReport(ReportType.EMPLOYEE_REPORT)) new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
    }

    @FXML
    void btnGenerateInventoryReport(ActionEvent event) {

        if (!reportService.generateReport(ReportType.INVENTORY_REPORT)) new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
    }

    @FXML
    void btnGenerateSalesReport(ActionEvent event) {

    }

    @FXML
    void btnGenerateSupplierReport(ActionEvent event) {

        if (!reportService.generateReport(ReportType.SUPPLIER_REPORT)) new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportService = ServiceFactory.getInstance().getService(ServiceType.REPORT);

        UserService userService = ServiceFactory.getInstance().getService(ServiceType.USER);
        sectionSalesReport.setVisible(userService.lastLogIsAdmin());
    }
}
