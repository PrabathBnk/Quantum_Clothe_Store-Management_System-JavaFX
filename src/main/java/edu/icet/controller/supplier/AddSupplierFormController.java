package edu.icet.controller.supplier;

import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.SupplierDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.SupplierService;
import edu.icet.util.GenerateIdUtil;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupplierFormController implements Initializable {

    @FXML
    private Label lblSupplierId;

    @FXML
    private JFXTextField txtCompanyName;

    @FXML
    private JFXTextField txtEmailAddress;

    @FXML
    private JFXTextField txtName;

    private SupplierService service;

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        boolean isSupplierAdded = service.addNewSupplier(new SupplierDto(
                lblSupplierId.getText(),
                txtName.getText(),
                txtCompanyName.getText(),
                txtEmailAddress.getText(),
                null
        ));

        if (isSupplierAdded) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier Added Successfully!").showAndWait();
            Node node = (Node) event.getTarget();
            Stage stage = (Stage) node.getScene().getWindow();
            SupplierFormController sfc = (SupplierFormController) stage.getUserData();
            sfc.getTableView().getSelectionModel().clearSelection();
            sfc.loadTable();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        lblSupplierId.setText(GenerateIdUtil.generateId("SUP", service.getLastSupplierId()));
    }
}
