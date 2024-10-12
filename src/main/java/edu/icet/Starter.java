package edu.icet;

import edu.icet.util.LoadFontUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/login_form.fxml")));
        LoadFontUtil.loadFontToScene(scene);
        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.show();
    }
}
