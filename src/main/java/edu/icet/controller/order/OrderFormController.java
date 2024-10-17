package edu.icet.controller.order;

import edu.icet.dto.OrderDto;
import edu.icet.util.SceneSwitcher;
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
    void btnPlaceOrderOnAction(ActionEvent event) {
        Stage stage = SceneSwitcher.showModal(mainPane.getScene().getWindow(), "../../../view/place_order_modal_form.fxml");
        stage.setUserData(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colNetTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));


    }

    void loadTable(){

    }

    TableView<OrderDto> getTableView(){
        return tblOrders;
    }
}
