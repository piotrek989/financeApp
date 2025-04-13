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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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

    public boolean validateDataInFields() {
        String email = emailField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();

        if (email == null || !email.contains("@")) {
            showAlert("Nieprawidłowy email", "Email musi zawierać znak '@'.");
            return false;
        }

        if (login == null || login.length() < 5) {
            showAlert("Nieprawidłowy login", "Login musi mieć co najmniej 5 znaków.");
            return false;
        }

        if (password == null || password.length() < 8) {
            showAlert("Nieprawidłowe hasło", "Hasło musi mieć co najmniej 8 znaków.");
            return false;
        }

        boolean hasLetter = password.matches(".*[a-zA-Z]+.*");
        boolean hasDigit = password.matches(".*\\d+.*");

        if (!hasLetter || !hasDigit) {
            showAlert("Nieprawidłowe hasło", "Hasło musi zawierać litery i przynajmniej jedną cyfrę.");
            return false;
        }

        return true;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public boolean validateIfMailAndLoginExists() {
        PresenterFacade facade = new PresenterFacade();
        return facade.validateIfMailAndLoginNotExists(email, login);
    }

    public void switchToSingInView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SingIn.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow(); // Pobierz aktualne okno
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); // Pokaż nową scenę
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) throws IOException {
        getDataFromFields();
        boolean correctValidation = validateDataInFields();
        if(correctValidation) {
            boolean ifEvenExists = validateIfMailAndLoginExists();
            if(ifEvenExists) {
                PresenterFacade facade = new PresenterFacade();
                boolean ifaddedUser = facade.createAccount(email, password, login);
                if (ifaddedUser) {
                    switchToLogInUI(event);//po utworzeniu konta wracamy do okna logowania
                }
            } else {
                showAlert("Blad", "Istnieje juz uzytkownik o podanym loginie i mailu");
            }
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
