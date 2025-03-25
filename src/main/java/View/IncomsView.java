package View;

import Model.Income;
import Model.User;
import Presenter.PresenterFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Text;


import java.io.IOException;
import java.util.ArrayList;

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
    Label userNameLabel;

    @FXML
    GridPane gridPaneValue;


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
        this.userNameLabel.setText("Welcome "+login);


    }

    @FXML
    public void setTextOnGridPaneTop3Incomes() {
        System.out.println("ŁADOWANIE!!!");
        PresenterFacade presenterFacade = new PresenterFacade();
        ArrayList<Income> incomes = presenterFacade.getTopValueItemsOfUser(user.id);

        for (int i = 0; i < incomes.size(); i++) {
            Label price1 = new Label(String.valueOf(incomes.get(i).price));
            Label date1 = new Label(String.valueOf(incomes.get(i).date));
            Label type1 = new Label(String.valueOf(incomes.get(i).type));

            gridPaneValue.add(price1, 1, i + 1);//col/row
            gridPaneValue.add(date1, 2, i + 1);
            gridPaneValue.add(type1, 3, i + 1);
        }

    }

    @FXML
    public void switchToIncomsView (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(View.FinanceApplication.class.getResource("Incoms.fxml"));

        Parent root = loader.load();

        IncomsView incomsViewController = loader.getController();

        incomsViewController.setUser(user);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        incomsViewController.setUserLoginOnLabel();

    }

//    @FXML
//    public void initialize() {
//        System.out.println("Inicjalizacja widoku...");
//        setAllGridPanes();
//    }
    private void setAllGridPanes(){
        //setting stuff
        setTextOnGridPaneTop3Incomes();

    }

    private void setUser (User user){
        this.user = user;
    }



}