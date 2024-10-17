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
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderModalController implements Initializable {

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
    private Text lblNetTotal;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblProductId;

    @FXML
    private Label lblProductName;

    @FXML
    private BorderPane pane;

    @FXML
    private TableView<OrderDetailTblDto> tblProducts;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private DatePicker dateReturnDate;

    private OrderService orderService;
    private List<OrderDetailTblDto> orderDetailTblDtoList;
    private OrderDetailTblDto orderDetail;

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        if (null==orderDetail) return;
        if (orderDetailTblDtoList.size() == 1){
            new Alert(Alert.AlertType.ERROR, "Product list cannot be empty!").show();
            return;
        }
        orderDetailTblDtoList.remove(orderDetail.getNum()-1);
        for (int i = orderDetail.getNum(); i < orderDetailTblDtoList.size(); i++) {
            orderDetailTblDtoList.get(i-1).setNum(i);
        }

        tblProducts.setItems(FXCollections.observableArrayList(orderDetailTblDtoList));
        clearFields();
        updateNetTotal();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm deletion?");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            if (orderService.deleteOrder(OrderFormController.orderDto)) {
                new Alert(Alert.AlertType.INFORMATION, "Order Deleted Successfully!").showAndWait();
                Stage stage = (Stage) pane.getScene().getWindow();
                OrderFormController ofc =(OrderFormController) stage.getUserData();
                ofc.loadTable();
                stage.close();
            }
        }
    }

    @FXML
    void btnSearchItemOnAction(ActionEvent event) {

        for (OrderDetailTblDto orderDetailTblDto : orderDetailTblDtoList) {
            if (orderDetailTblDto.getProductId().equals(txtSearchBar.getText())) {
                setSelectedProduct(orderDetailTblDto);
                return;
            }
        }

        new Alert(Alert.AlertType.ERROR, "Product not found!").show();
        clearFields();

    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        if (null==orderDetail) return;
        Integer qty = Integer.parseInt(txtQuantity.getText());
        orderDetailTblDtoList.get(orderDetail.getNum()-1).setQty(qty);
        orderDetailTblDtoList.get(orderDetail.getNum()-1).setTotalAmount(orderDetail.getUnitPrice() * qty);
        tblProducts.setItems(FXCollections.observableArrayList(orderDetailTblDtoList));
        tblProducts.refresh();
        clearFields();
        updateNetTotal();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (null==cmbPaymentType.getValue()) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment type").show();
            return;
        }

        boolean isOrderUpdated = orderService.updateOrder(new OrderDto(
                lblOrderId.getText(),
                Double.parseDouble(lblNetTotal.getText()),
                LocalDate.now().toString(),
                dateReturnDate.getValue().toString(),
                cmbPaymentType.getValue(),
                null
        ));

        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
        for (OrderDetailTblDto orderDetailTblDto: orderDetailTblDtoList) {
            orderDetailDtoList.add(new OrderDetailDto(
                    lblOrderId.getText(),
                    orderDetailTblDto.getProductId(),
                    orderDetailTblDto.getQty()
            ));
        }
        boolean isOrderDetailsUpdated = orderService.updateOrderDetails(orderDetailDtoList);

        if (isOrderUpdated && isOrderDetailsUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Order Updated Successfully!").showAndWait();
            Stage stage = (Stage) pane.getScene().getWindow();
            OrderFormController ofc =(OrderFormController) stage.getUserData();
            ofc.getTableView().getSelectionModel().clearSelection();
            ofc.loadTable();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        orderService = ServiceFactory.getInstance().getService(ServiceType.ORDERS);
        orderDetailTblDtoList = orderService.getOrderDetails(OrderFormController.orderDto.getOrderID());
        tblProducts.setItems(FXCollections.observableArrayList(orderDetailTblDtoList));

        //Set Order ID
        lblOrderId.setText(OrderFormController.orderDto.getOrderID());

        //Set Payment type to combo box
        PaymentTypeService paymentTypeService = ServiceFactory.getInstance().getService(ServiceType.PAYMENT_TYPE);
        cmbPaymentType.setItems(FXCollections.observableArrayList(paymentTypeService.getAllPaymentTypeNames()));

        dateReturnDate.setValue(LocalDate.parse(OrderFormController.orderDto.getReturnDate()));

        tblProducts.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            tblProducts.getSelectionModel().clearSelection();
            setSelectedProduct(newValue);
        });

        cmbPaymentType.setValue(OrderFormController.orderDto.getPaymentType());
        lblNetTotal.setText(String.format("%.2f", OrderFormController.orderDto.getNetTotal()));
    }

    private void setSelectedProduct(OrderDetailTblDto orderDetailTblDto){
        try {
            lblProductId.setText(orderDetailTblDto.getProductId());
            lblProductName.setText(orderDetailTblDto.getProductName());
            txtQuantity.setText(orderDetailTblDto.getQty().toString());
            orderDetail = orderDetailTblDto;
        } catch (RuntimeException exception) {
            clearFields();
        }
    }

    private void clearFields() {
        lblProductId.setText(null);
        lblProductName.setText(null);
        txtQuantity.setText(null);
        orderDetail = null;
    }

    private void updateNetTotal() {
        double netTotal = 0.0;
        for(OrderDetailTblDto orderDetailTblDto : tblProducts.getItems()) {
            netTotal += orderDetailTblDto.getQty() * orderDetailTblDto.getUnitPrice();
        }

        lblNetTotal.setText(String.format("%.2f", netTotal));
    }

}
