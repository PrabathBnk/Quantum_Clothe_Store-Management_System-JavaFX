package edu.icet.controller.report;

import com.jfoenix.controls.JFXListView;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.ReportService;
import edu.icet.service.custom.UserService;
import edu.icet.util.PdfViewer;
import edu.icet.util.ReportType;
import edu.icet.util.SceneSwitcher;
import edu.icet.util.ServiceType;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ReportFormController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private VBox sectionSalesReport;

    @FXML
    private JFXListView<String> listEmployeeReport;

    @FXML
    private JFXListView<String> listInventoryReport;

    @FXML
    private JFXListView<String> listSalesReport;

    @FXML
    private JFXListView<String> listSupplierReport;

    private ReportService reportService;
    private HashMap<String,File> inventoryReports;
    File inventoryReportDir;

    private HashMap<String,File> employeeReports;
    File employeeReportDir;

    private HashMap<String,File> supplierReports;
    File supplierReportDir;


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
        setHashMapToListView(employeeReports, employeeReportDir, listEmployeeReport);
    }

    @FXML
    void btnGenerateInventoryReport(ActionEvent event) {
        if (!reportService.generateReport(ReportType.INVENTORY_REPORT)) new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        setHashMapToListView(inventoryReports, inventoryReportDir, listInventoryReport);
    }

    @FXML
    void btnGenerateSalesReport(ActionEvent event) {

    }

    @FXML
    void btnGenerateSupplierReport(ActionEvent event) {
        if (!reportService.generateReport(ReportType.SUPPLIER_REPORT)) new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        setHashMapToListView(supplierReports, supplierReportDir, listSupplierReport);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportService = ServiceFactory.getInstance().getService(ServiceType.REPORT);

        UserService userService = ServiceFactory.getInstance().getService(ServiceType.USER);
        sectionSalesReport.setVisible(userService.lastLogIsAdmin());

        inventoryReports = new HashMap<>();
        inventoryReportDir = new File("src/main/resources/generated_reports/inventory_reports");
        setHashMapToListView(inventoryReports, inventoryReportDir, listInventoryReport);

        employeeReports = new HashMap<>();
        employeeReportDir = new File("src/main/resources/generated_reports/employee_reports");
        setHashMapToListView(employeeReports, employeeReportDir, listEmployeeReport);

        supplierReports = new HashMap<>();
        supplierReportDir = new File("src/main/resources/generated_reports/supplier_reports");
        setHashMapToListView(supplierReports, supplierReportDir, listSupplierReport);
    }

    private void setHashMapToListView(HashMap<String,File> reportsHashMap, File reportsDirectory, JFXListView<String> listView){
        for (File file : reportsDirectory.listFiles()) {
            reportsHashMap.put(file.getName(), file);
        }
        listView.setItems(FXCollections.observableArrayList(reportsDirectory.list()));
        listView.getSelectionModel().getSelectedItems().addListener((InvalidationListener) report -> {
            File file = reportsHashMap.get(report.toString().replaceAll("[\\[\\]]", ""));
            PdfViewer.getInstance().viewPdf(file.getPath());
            report = null;
        });

    }
}
