package edu.icet.controller.order;

import edu.icet.dto.OrderDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.OrderService;
import edu.icet.service.custom.UserService;
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

public class OrderFormController implements Initializable {

    static OrderDto orderDto;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colNetTotal;

    @FXML
    private TableColumn<?, ?> colNum;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colPaymentType;

    @FXML
    private BorderPane mainPane;

    @FXML
    private TableView<OrderDto> tblOrders;


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
    public void btnSlideBarDashboard(ActionEvent actionEvent) {
        UserService userService = ServiceFactory.getInstance().getService(ServiceType.USER);
        SceneSwitcher.switchSceneTo((Stage) mainPane.getScene().getWindow(),  userService.lastLogIsAdmin() ? SceneSwitcher.ADMIN_DASHBOARD: SceneSwitcher.GENERAL_DASHBOARD);
    }


    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/place_order_modal_form.fxml");
        stage.setUserData(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colNetTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));

        loadTable();

        tblOrders.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            orderDto = newValue;
            Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/order_modal_form.fxml");
            stage.setUserData(this);
        });
    }

    void loadTable(){
        OrderService service = ServiceFactory.getInstance().getService(ServiceType.ORDERS);
        tblOrders.setItems(FXCollections.observableArrayList(service.getAllOrders()));
    }

    TableView<OrderDto> getTableView(){
        return tblOrders;
    }
}
