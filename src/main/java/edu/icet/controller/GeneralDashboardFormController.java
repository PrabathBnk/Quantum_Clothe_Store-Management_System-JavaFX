package edu.icet.controller;

import edu.icet.dto.OrderDto;
import edu.icet.dto.ProductDto;
import edu.icet.dto.UserTableDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.*;
import edu.icet.util.SceneSwitcher;
import edu.icet.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GeneralDashboardFormController implements Initializable {

    @FXML
    private Label lblTotalItems;

    @FXML
    private Label lblTotalOrderRevenue;

    @FXML
    private Label lblTotalOrders;

    @FXML
    private Label lblTotalOutOfStockItems;

    @FXML
    private Label lblTotalSuppliers;

    @FXML
    private Text lblUserEmailAddress;

    @FXML
    private Text lblUserName;

    @FXML
    private BorderPane mainPane;

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
    void btnLogOutOnAction(ActionEvent event) {
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(), "../../../view/login_form.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserService userService = ServiceFactory.getInstance().getService(ServiceType.USER);
        List<UserTableDto> allUsers = userService.getAllUsers();
        UserLogService userLogService = ServiceFactory.getInstance().getService(ServiceType.USER_LOG);
        String lastLoggedUserID = userLogService.lastLoggedUser();
        for (UserTableDto user: allUsers){
            if (user.getUserID().equals(lastLoggedUserID)) {
                lblUserName.setText(user.getName());
                lblUserEmailAddress.setText(user.getEmailAddress());
                break;
            }
        }

        SupplierService supplierService = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        lblTotalSuppliers.setText(String.format("%02d", supplierService.getAllSuppliers().size()));

        OrderService orderService = ServiceFactory.getInstance().getService(ServiceType.ORDERS);
        List<OrderDto> allOrders = orderService.getAllOrders();
        lblTotalOrders.setText(String.format("%02d", allOrders.size()));
        Double totalRevenue = 0.0;
        for (OrderDto order: allOrders) {
            totalRevenue += order.getNetTotal();
        }

        lblTotalOrderRevenue.setText(String.format("%3.0f", totalRevenue > 1000 ? totalRevenue/1000 : totalRevenue) + (totalRevenue > 1000 ? "K":""));

        ProductService productService = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        List<ProductDto> allProducts = productService.getAllProducts();
        lblTotalItems.setText(String.format("%02d", allProducts.size()));

        Integer outOfStocktems = 0;
        for (ProductDto product: allProducts) {
            if (product.getQuantityOnHand()==0) outOfStocktems++;
        }
        lblTotalOutOfStockItems.setText(String.format("%02d", outOfStocktems));
    }
}
