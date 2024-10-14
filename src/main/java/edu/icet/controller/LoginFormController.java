package edu.icet.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.CreateAccountDto;
import edu.icet.dto.LoginDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.UserService;
import edu.icet.util.LoadFontUtil;
import edu.icet.util.SceneSwitcher;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
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

    private final UserService service = ServiceFactory.getInstance().getService(ServiceType.USER);

    @FXML
    void btnBackOnAction(ActionEvent event) {
        createAccountPane.setVisible(false);
        confirmUserPane.setVisible(false);
        loginPane.setVisible(true);
    }

    @FXML
    void btnCreateAccountOnAction(ActionEvent event) {
        CreateAccountDto createAccountDto = new CreateAccountDto(
                txtEmailCreateAccount.getText(),
                txtPasswordCreateAccount.getText(),
                txtConfirmPassword.getText(),
                cmbAccountTypeCreateAccount.getValue()
        );

        if (service.createUserAccount(createAccountDto)) {
            new Alert(Alert.AlertType.INFORMATION, "User account created successfully!").showAndWait();
            createAccountPane.setVisible(false);
            loginPane.setVisible(true);
        }
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        boolean authenticated = service.authenticateUser(new LoginDto(txtEmail.getText(), txtPassword.getText(), cmbAccountType.getValue()));

        if(authenticated){
            Stage stage = (Stage) mainPane.getScene().getWindow();

            if ("Admin".equals(cmbAccountType.getValue())) {
                SceneSwitcher.switchSceneTo(stage, SceneSwitcher.ADMIN_DASHBOARD);
            } else {
                SceneSwitcher.switchSceneTo(stage, SceneSwitcher.GENERAL_DASHBOARD);
            }

        }
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

        if (service.verifyOTP(txtOTP.getText())) {
            otpConfirmPane.setVisible(false);
            resetPasswordPane.setVisible(true);
        }
    }

    public void btnSendOTPOnAction(ActionEvent actionEvent) {

        if (service.sendOTPToUser(txtEmailConfirmUser.getText())) {
            confirmUserPane.setVisible(false);
            otpConfirmPane.setVisible(true);

        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void btnConfirmOTPBackOnAction(ActionEvent actionEvent) {
        otpConfirmPane.setVisible(false);
        confirmUserPane.setVisible(true);
    }

    public void btnResetPasswordOnAction(ActionEvent actionEvent) {

        boolean isPasswordUpdated = service.updatePassword(txtEmailConfirmUser.getText(), txtPasswordResetPwd.getText(), txtConfirmPasswordResetPwd.getText());

        if (isPasswordUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").showAndWait();
            resetPasswordPane.setVisible(false);
            loginPane.setVisible(true);
        }
    }

    public void lblResendOtpOnClick(MouseEvent mouseEvent) {

        if (service.sendOTPToUser(txtEmailConfirmUser.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "New OTP Sent!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }
}
