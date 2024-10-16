package edu.icet.controller.supplier;

import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.ProductDto;
import edu.icet.dto.SupplierDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.ProductService;
import edu.icet.service.custom.SupplierService;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SupplierModalFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colNum;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;


    @FXML
    private Label lblSupplierId;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private JFXTextField txtCompanyName;

    @FXML
    private JFXTextField txtEmailAddress;

    @FXML
    private JFXTextField txtName;

    @FXML
    private TableView<ProductDto> tblProducts;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        SupplierService service = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        boolean isSupplierDeleted = service.deleteSupplier(new SupplierDto(
                lblSupplierId.getText(),
                txtName.getText(),
                txtCompanyName.getText(),
                txtEmailAddress.getText(),
                null
        ));

        if (isSupplierDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted Successfully!");
            closeModal(event);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        SupplierService service = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        boolean isSupplierUpdated = service.updateSupplier(new SupplierDto(
                lblSupplierId.getText(),
                txtName.getText(),
                txtCompanyName.getText(),
                txtEmailAddress.getText(),
                null
        ));

        if (isSupplierUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully!").showAndWait();
            closeModal(event);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblSupplierId.setText(SupplierFormController.supplierDto.getSupplierID());
        txtName.setText(SupplierFormController.supplierDto.getName());
        txtCompanyName.setText(SupplierFormController.supplierDto.getCompanyName());
        txtEmailAddress.setText(SupplierFormController.supplierDto.getEmailAddress());

        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        ProductService productService = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        tblProducts.setItems(FXCollections.observableArrayList(productService.getAllProductsOfSupplier(SupplierFormController.supplierDto.getSupplierID())));
    }

    private void closeModal(Event event) {
        Node node = (Node) event.getTarget();
        Stage stage = (Stage) node.getScene().getWindow();
        SupplierFormController sfc = (SupplierFormController) stage.getUserData();
        sfc.loadTable();
        sfc.getTableView().getSelectionModel().clearSelection();
        stage.close();
    }
}
