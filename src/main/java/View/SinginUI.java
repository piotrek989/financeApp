package View;

import Presenter.PresenterFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SinginUI {
    private Scene scene;
    private Parent root;
    String login;
    String password;
    String email;
    @FXML
    private TextField emailField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;


    public void getDataFromFields(){
        email = emailField.getText().trim();
        login = loginField.getText().trim();
        password = passwordField.getText().trim();
    }
    public void cleanDataFromFields(){
        emailField.clear();
        loginField.clear();
        passwordField.clear();
    }

    public void switchToSingInView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SingIn.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow(); // Pobierz aktualne okno
        Scene scene = new Scene(root);
        stage.setTitle("---Signing In---");
        stage.setScene(scene);
        stage.show(); // Pokaż nową scenę
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) throws IOException {
        getDataFromFields();
        PresenterFacade facade = new PresenterFacade();
        boolean ifaddedUser = facade.createAccount(email, password, login);

        if (ifaddedUser) {
            switchToLogInUI(event);//po utworzeniu konta wracamy do okna logowania
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) throws IOException {
        cleanDataFromFields();
    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException{
        switchToLogInUI(event);
    }

    private void switchToLogInUI(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Pobierz aktualne okno
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); // Pokaż nową scenę
    }

}
