package edu.icet.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public VBox resetPasswordPane;
    public JFXTextField txtOTP;
    public VBox otpConfirmPane;
    public JFXTextField txtEmailResetPassword;
    public JFXPasswordField txtConfirmPasswordResetPwd;
    public JFXPasswordField txtPasswordResetPwd;
    public JFXTextField txtEmailConfirmUser;
    public VBox confirmUserPane;
    public JFXPasswordField txtConfirmPassword;
    @FXML
    private JFXComboBox<String> cmbAccountType;

    @FXML
    private JFXComboBox<String> cmbAccountTypeCreateAccount;

    @FXML
    private VBox createAccountPane;

    @FXML
    private VBox loginPane;

    @FXML
    private Pane mainPane;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtEmailCreateAccount;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXPasswordField txtPasswordCreateAccount;

    @FXML
    void btnBackOnAction(ActionEvent event) {
        createAccountPane.setVisible(false);
        confirmUserPane.setVisible(false);
        loginPane.setVisible(true);
    }

    @FXML
    void btnCreateAccountOnAction(ActionEvent event) {

    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {

    }

    @FXML
    void lblCreateAcountOnClick(MouseEvent event) {
        loginPane.setVisible(false);
        createAccountPane.setVisible(true);
    }

    @FXML
    void lblForgetPwdOnClick(MouseEvent event) {
        loginPane.setVisible(false);
        confirmUserPane.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> accountTypes = FXCollections.observableArrayList();

        accountTypes.add("General");
        accountTypes.add("Admin");

        cmbAccountType.setItems(accountTypes);
        cmbAccountTypeCreateAccount.setItems(accountTypes);
    }

    public void btnConfirmOnAction(ActionEvent actionEvent) {
        otpConfirmPane.setVisible(false);
        resetPasswordPane.setVisible(true);
    }

    public void btnSendOTPOnAction(ActionEvent actionEvent) {
        confirmUserPane.setVisible(false);
        otpConfirmPane.setVisible(true);
    }

    public void btnConfirmOTPBackOnAction(ActionEvent actionEvent) {
        otpConfirmPane.setVisible(false);
        confirmUserPane.setVisible(true);
    }

    public void btnResetPasswordOnAction(ActionEvent actionEvent) {
    }
}
