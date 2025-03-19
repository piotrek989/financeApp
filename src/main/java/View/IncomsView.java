package View;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class IncomsView{
    public IncomsView(User user){
        this.user = user;
        System.out.println(user.login+" "+user.email);
    }
    public IncomsView(){

    }

    public User user;
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
    Pane backgroundPane;
    @FXML
    Label welLabel;


    private void reqFocusPane(){
        backgroundPane.setFocusTraversable(true);
        backgroundPane.requestFocus();
    }

    @FXML
    private void handlebtnLogout(ActionEvent event) throws IOException {
        var LoginUI = new LoginUI();
        LoginUI.switchToLoginView(event);
    }
    @FXML
    public void handlebtnIncoms(ActionEvent event) throws IOException {
        var IncomsView = new IncomsView(user);
        IncomsView.switchToIncomsView(event);
        reqFocusPane();
    }
    @FXML
    public void handlebtnExpenses(ActionEvent event) throws IOException {
        var ExpensesView = new ExpensesView();
        ExpensesView.switchToExpensesView(event);
        reqFocusPane();
    }
    @FXML
    public void handlebtnCharts(ActionEvent event) throws IOException {
        var ChartsView = new ChartsView();//we dont pass the user yet
        ChartsView.switchToChartsView(event);
        reqFocusPane();
    }

    @FXML
    public void setUserLoginOnLabel(){//this metod is inherited because public
        String login = user.login;
        System.out.println("Oto login: "+login);
        this.welLabel.setText("Welcome "+login);
    }

    @FXML
    public void switchToIncomsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(View.FinanceApplication.class.getResource("Incoms.fxml"));

        Parent root = loader.load();

        IncomsView incomsViewController = loader.getController();

        incomsViewController.setUser(user);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("---Incoms View---");
        stage.setScene(scene);
        stage.show();

        incomsViewController.setUserLoginOnLabel();
    }

    private void setUser(User user) {
        this.user = user;
    }


}

//
//public class IncomsView extends BaseUserView{
//    public IncomsView(User user){
//        super(user);
//    }
//}