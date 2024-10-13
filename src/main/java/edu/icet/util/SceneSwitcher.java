package edu.icet.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static final String ADMIN_DASHBOARD = "../../../view/admin_dashboard.fxml";
    public static final String GENERAL_DASHBOARD = "../../../view/general_dashboard.fxml";
    public static final String EMPLOYEE = "../../../view/employee_form.fxml";
    public static final String USER = "../../../view/user_form.fxml";
    public static final String ORDER = "../../../view/order_form.fxml";
    public static final String PRODUCT = "../../../view/product_form.fxml";
    public static final String SUPPLIER = "../../../view/supplier_form.fxml";
    public static final String REPORT = "../../../view/report_form.fxml";

    public static void switchSceneTo(Stage stage, String path) {

        try {
            Scene scene = new Scene(FXMLLoader.load(SceneSwitcher.class.getResource(path)));
            LoadFontUtil.loadFontToScene(scene);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}