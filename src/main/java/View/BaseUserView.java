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
import javafx.stage.Stage;

import java.io.IOException;

public class BaseUserView {
    private Scene scene;
    private Parent root;

    @FXML
    Button logoutButton;

    @FXML
    Button incomeButton;

    @FXML
    Button expenseButton;

    @FXML
    Button chartButton;


    @FXML
    private void handlebtnLogout(ActionEvent event) throws IOException {
        var LoginUI = new LoginUI();
        LoginUI.switchToLoginView(event);
    }
    @FXML
    private void handlebtnIncoms(ActionEvent event) throws IOException {
        var IncomsView = new IncomsView();
        IncomsView.switchToIncomsView(event);
    }
    @FXML
    private void handlebtnExpenses(ActionEvent event) throws IOException {
        var ExpensesView = new ExpensesView();
        ExpensesView.switchToExpensesView(event);
    }
    @FXML
    private void handlebtnCharts(ActionEvent event) throws IOException {
        var ChartsView = new ChartsView();
        ChartsView.switchToChartsView(event);
    }
}
