package edu.icet.controller.supplier;

import edu.icet.dto.SupplierDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.SupplierService;
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

public class SupplierFormController implements Initializable {

    static SupplierDto supplierDto;

    @FXML
    private TableColumn<?, ?> colCompanyName;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNum;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colemailAddress;

    @FXML
    private TableView<SupplierDto> tblSuppliers;

    @FXML
    private BorderPane mainPane;


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
    void btnAddSupplierOnAction(ActionEvent event) {
        Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/add_supplier_form.fxml");
        stage.setUserData(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colemailAddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));

        loadTable();

        tblSuppliers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            supplierDto = newValue;
            Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/supplier_modal_form.fxml");
            stage.setUserData(this);
        });
    }

    void loadTable(){
        SupplierService service = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        tblSuppliers.setItems(FXCollections.observableArrayList(service.getAllSuppliers()));
    }

    TableView<SupplierDto> getTableView() {
        return tblSuppliers;
    }
}
