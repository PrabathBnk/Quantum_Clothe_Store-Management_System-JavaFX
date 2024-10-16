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

public class AddProductFormController implements Initializable {

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
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private BorderPane pane;

    private ProductService service;
    private File productImage;

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        boolean isProductAdded;
        try {
            isProductAdded = service.addNewProduct(new ProductDto(
                    lblProductId.getText(),
                    txtName.getText(),
                    cmbSize.getValue(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtQuantity.getText()),
                    productImage.getPath(),
                    cmbCategory.getValue(),
                    cmbSupplierId.getValue(),
                    null
            ));
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong! Check input details and try again.").show();
            isProductAdded = false;
        }

        if (isProductAdded) {
            new Alert(Alert.AlertType.INFORMATION, "Product Added Successfully!").showAndWait();
            Stage stage = (Stage) pane.getScene().getWindow();
            ProductFormController pfc = (ProductFormController) stage.getUserData();
            pfc.getTableView().getSelectionModel().clearSelection();
            pfc.loadTable();
            stage.close();
        }
    }

    @FXML
    void onLblImageOnClick(MouseEvent event) {
        productImage = new FileChooser().showOpenDialog(pane.getScene().getWindow());
        imageView.setImage(new Image(productImage.getPath()));
    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) {
        SupplierService supplierService = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        lblSupplierName.setText(supplierService.getSupplierNameById(cmbSupplierId.getValue()));
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
    }
}
