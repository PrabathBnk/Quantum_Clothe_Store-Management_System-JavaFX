package edu.icet.controller.product;

import edu.icet.dto.ProductDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.ProductService;
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

public class ProductFormController implements Initializable {

    static ProductDto productDto;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNum;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private BorderPane mainPane;

    @FXML
    private TableView<ProductDto> tblProducts;

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
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), SceneSwitcher.ADMIN_DASHBOARD);
    }


    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/add_product_form.fxml");
        stage.setUserData(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityOnHand"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("productSize"));

        loadTable();

        tblProducts.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            productDto = newValue;
            Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/product_modal_form.fxml");
            stage.setUserData(this);

        });

    }

    void loadTable(){
        ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        tblProducts.setItems(FXCollections.observableArrayList(service.getAllProducts()));
    }

    TableView<ProductDto> getTableView(){
        return tblProducts;
    }
}
