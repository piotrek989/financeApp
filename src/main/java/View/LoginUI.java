package View;

import Model.User;
import Presenter.PresenterFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginUI extends IncomsView{

    String login;
    String password;
    Stage stage;

    @FXML
    private Button logInBtn;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    public void getDataFromField(){
        login = loginField.getText().trim();
        password = passwordField.getText().trim();
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void switchToLoginView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(View.FinanceApplication.class.getResource("LogIn.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow(); // Pobierz aktualne okno
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }


    @FXML
    private void handleButtonClick(ActionEvent event){
        getDataFromField();
        PresenterFacade facade = new PresenterFacade();
        User user = facade.logIn(login, password);
        if(user != null){
            var IncomsView = new IncomsView(user);
            try {
                IncomsView.switchToIncomsView(event);
            } catch (IOException e) {
                e.printStackTrace(); // Możesz też dodać Alert z komunikatem błędu
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Alert Dialog");
            alert.setContentText("There is no such user in DATABASE. Try again!");
            alert.showAndWait();
        }

    }

    @FXML
    private void hyperlinkOperator(ActionEvent event) throws IOException {
        var SinginUI = new SinginUI();
        SinginUI.switchToSingInView(event);
    }

}
