package edu.icet.controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.OrderDetailDto;
import edu.icet.dto.OrderDetailTblDto;
import edu.icet.dto.OrderDto;
import edu.icet.dto.ProductDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.OrderService;
import edu.icet.service.custom.PaymentTypeService;
import edu.icet.service.custom.ProductService;
import edu.icet.util.GenerateIdUtil;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderModalFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbPaymentType;

    @FXML
    private TableColumn<?, ?> colNum;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colTotalAmount;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Text lblBalance;

    @FXML
    private Text lblNetTotal;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblProductId;

    @FXML
    private Label lblProductName;

    @FXML
    private Label lblProductPrice;

    @FXML
    private Label lblProductSize;

    @FXML
    private BorderPane pane;

    @FXML
    private TableView<OrderDetailTblDto> tblProducts;

    @FXML
    private JFXTextField txtPaymentAmount;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private TextField txtSearchBar;

    private ProductDto product;
    private List<OrderDetailTblDto> orderDetailList;

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        if (isProductExistInList(product)) {
            new Alert(Alert.AlertType.ERROR, "This product already in the list!").show();
            return;
        }

        try {
            String qty = txtQuantity.getText();
            if(!qty.isEmpty()) {
                orderDetailList.add(new OrderDetailTblDto(
                        tblProducts.getItems().size()+1,
                        product.getProductID(),
                        product.getName(),
                        product.getPrice(),
                        Integer.parseInt(qty),
                        totalAmount(Integer.parseInt(qty), product.getPrice())
                ));
                tblProducts.setItems(FXCollections.observableArrayList(orderDetailList));
                product = null;
                updateNetTotal();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }


    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if (null==cmbPaymentType.getValue()) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment type").show();
            return;
        }

        if (txtPaymentAmount.getText().isEmpty() || Double.parseDouble(lblBalance.getText()) < 0){
            new Alert(Alert.AlertType.ERROR, "Invalid payment amount.").show();
            return;
        }

        if (tblProducts.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Product list is empty!").show();
            return;
        }

        OrderService orderService = ServiceFactory.getInstance().getService(ServiceType.ORDERS);
        boolean isOrderPlaced = orderService.placeOrder(new OrderDto(
                lblOrderId.getText(),
                Double.parseDouble(lblNetTotal.getText()),
                LocalDate.now().toString(),
                null,
                cmbPaymentType.getValue(),
                null
        ));

        List<OrderDetailDto> orderDetailList = new ArrayList<>();
        for (OrderDetailTblDto orderDetailTblDto: tblProducts.getItems()) {
            orderDetailList.add(new OrderDetailDto(
                    lblOrderId.getText(),
                    orderDetailTblDto.getProductId(),
                    orderDetailTblDto.getQty()
            ));
        }

        boolean isOrderDetailsSaved = orderService.saveOrderDetails(orderDetailList);

        if (isOrderPlaced && isOrderDetailsSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order Placement Successful!").showAndWait();
            Stage stage = (Stage) pane.getScene().getWindow();
            OrderFormController ofc = (OrderFormController) stage.getUserData();
            ofc.getTableView().getSelectionModel().clearSelection();
            ofc.loadTable();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    @FXML
    void btnSearchProductOnAction(ActionEvent event) {
        ProductService productService = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        product = productService.getProduct(txtSearchBar.getText());

        if(null!=product) {
            lblProductId.setText(product.getProductID());
            lblProductName.setText(product.getName());
            lblProductSize.setText(product.getProductSize());
            lblProductPrice.setText(product.getPrice().toString());
        } else {
            lblProductId.setText(null);
            lblProductName.setText(null);
            lblProductSize.setText(null);
            lblProductPrice.setText(null);
            txtQuantity.setText(null);
            new Alert(Alert.AlertType.ERROR, "Item not found!").show();
        }
    }

    @FXML
    void txtPaymentAmountOnKeyReleased(KeyEvent event) {
        try{
            updateBalance();
        } catch (RuntimeException e){}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrderService service = ServiceFactory.getInstance().getService(ServiceType.ORDERS);

        //Set Order ID
        lblOrderId.setText(GenerateIdUtil.generateId("OID", service.getLastOrderID()));

        //Set Payment type to combo box
        PaymentTypeService paymentTypeService = ServiceFactory.getInstance().getService(ServiceType.PAYMENT_TYPE);
        cmbPaymentType.setItems(FXCollections.observableArrayList(paymentTypeService.getAllPaymentTypeNames()));


        orderDetailList = new ArrayList<>();

        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

    }

    private void updateNetTotal() {
        double netTotal = 0.0;
        for(OrderDetailTblDto orderDetail : tblProducts.getItems()) {
            netTotal += totalAmount(orderDetail.getQty(), orderDetail.getUnitPrice());
        }

        lblNetTotal.setText(String.format("%.2f", netTotal));
        updateBalance();
    }

    private Double totalAmount(int qty, Double price) {
        return qty * price;
    }

    private boolean isProductExistInList(ProductDto product) {
        for (OrderDetailTblDto orderDetailTblDto: orderDetailList){
            if (null==product || orderDetailTblDto.getProductId().equals(product.getProductID())){
                return true;
            }
        }
        return false;
    }

    private void updateBalance(){
        if (!txtPaymentAmount.getText().isEmpty()) {
            double balance = Double.parseDouble(txtPaymentAmount.getText()) - Double.parseDouble(lblNetTotal.getText());
            lblBalance.setText(String.format("%.2f", balance));
        }
    }
}
