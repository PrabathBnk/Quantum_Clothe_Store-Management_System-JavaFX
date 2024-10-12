package edu.icet.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.icet.dto.CreateAccountDto;
import edu.icet.dto.LoginDto;
import edu.icet.dto.UserDto;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.UserService;
import edu.icet.util.LoadFontUtil;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

            try {
                if (cmbAccountType.getValue().equals("Admin")) {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../../view/admin_dashboard.fxml")));
                    LoadFontUtil.loadFontToScene(scene);
                    stage.setScene(scene);
                } else {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/general_dashboard.fxml")));
                    LoadFontUtil.loadFontToScene(scene);
                    stage.setScene(scene);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
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
