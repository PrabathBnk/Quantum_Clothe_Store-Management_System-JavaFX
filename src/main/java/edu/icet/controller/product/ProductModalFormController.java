package edu.icet.controller.product;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.ProductDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CategoryService;
import edu.icet.service.custom.ProductService;
import edu.icet.service.custom.SupplierService;
import edu.icet.util.GenerateIdUtil;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductModalFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCategory;

    @FXML
    private JFXComboBox<String> cmbSize;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lblImage;

    @FXML
    private Label lblProductId;

    @FXML
    private Text lblSupplierName;

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQuantity;

    private ProductService service;
    private File productImage;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm deletion?");
        alert.showAndWait();

        if(alert.getResult().getText().equals("OK")) {
            if (service.deleteProductById(lblProductId.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully!").showAndWait();
                Stage stage = (Stage) pane.getScene().getWindow();
                ProductFormController pfc = (ProductFormController) stage.getUserData();
                pfc.loadTable();
                stage.close();
            }
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isProductUpdated;

        try {
            isProductUpdated = service.updateProduct(new ProductDto(
                    lblProductId.getText(),
                    txtName.getText(),
                    cmbSize.getValue(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtQuantity.getText()),
                    null!=productImage ? productImage.getPath(): ProductFormController.productDto.getImage(),
                    cmbCategory.getValue(),
                    cmbSupplierId.getValue(),
                    null
            ));

        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong! Check input details and try again.").show();
            isProductUpdated = false;
        }

        if(isProductUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Product updated successfully!").showAndWait();
            Stage stage = (Stage) pane.getScene().getWindow();
            ProductFormController pfc = (ProductFormController) stage.getUserData();
            pfc.loadTable();
            stage.close();
        }
    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) {
        SupplierService supplierService = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        lblSupplierName.setText(supplierService.getSupplierNameById(cmbSupplierId.getValue()));
    }

    @FXML
    void onLblImageOnClick(MouseEvent event) {

        imageView.setImage(new Image(new FileChooser().showOpenDialog(pane.getScene().getWindow()).getPath()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);

        //Set ProductID to Label
        lblProductId.setText(GenerateIdUtil.generateId("PID", service.getLastProductId()));

        //Set categories to Category combo box
        CategoryService categoryService = ServiceFactory.getInstance().getService(ServiceType.CATEGORY);
        cmbCategory.setItems(FXCollections.observableArrayList(categoryService.getAllCategoryNames()));

        //Set sizes to Size combo box
        cmbSize.setItems(FXCollections.observableArrayList("XS", "S", "M", "L", "XL", "XXL", "XXXL"));
        cmbSize.setValue("XS");

        //Set SupplierIDs to SupplierID combo box
        SupplierService supplierService = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        ObservableList<String> supplierIds = FXCollections.observableArrayList();
        supplierService.getAllSuppliers().forEach(supplierDto -> supplierIds.add(supplierDto.getSupplierID()));
        cmbSupplierId.setItems(supplierIds);


        //Product's details set to fields
        ProductDto productDto = ProductFormController.productDto;
        lblProductId.setText(productDto.getProductID());
        imageView.setImage(new Image(productDto.getImage()));
        txtName.setText(productDto.getName());
        txtPrice.setText(productDto.getPrice().toString());
        cmbSize.setValue(productDto.getProductSize());
        txtQuantity.setText(productDto.getQuantityOnHand().toString());
        cmbCategory.setValue(productDto.getCategory());
        cmbSupplierId.setValue(productDto.getSupplierID());
        lblSupplierName.setText(supplierService.getSupplierNameById(productDto.getSupplierID()));
    }
}
