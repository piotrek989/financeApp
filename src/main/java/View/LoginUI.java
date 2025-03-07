package View;

import Model.User;
import View.UserView;
import Presenter.PresenterFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginUI {

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


    @FXML
    private void handleButtonClick(ActionEvent event){
        getDataFromField();
        PresenterFacade facade = new PresenterFacade();
        User user = facade.logIn(login, password);
        if(user != null){
            var UserView = new UserView();
            try {
                UserView.switchToUserView(event);
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
