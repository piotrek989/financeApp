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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.w3c.dom.Text;


import java.io.IOException;
import java.util.ArrayList;

public class IncomsView{
    public IncomsView(User user){
        this.user = user;
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

    @FXML
    TextField dateField;

    @FXML
    TextField priceField;

    @FXML
    TextField typeField;

    @FXML
    Button addItemButton;


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
        switchToIncomsView(event);
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
    public void handleButtonAddItem(){
        Income income = getDataAboutIncome();
        var PresenterFacade = new PresenterFacade();

        if(!PresenterFacade.addIncome(income)){
            System.out.println("UWAGA nie dodano itemu");
        }
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

            // Stylowanie: większa czcionka i wyśrodkowanie
            styleLabel(price1);
            styleLabel(date1);
            styleLabel(type1);

            gridPaneValue.add(price1, 1, i + 1); // col / row
            gridPaneValue.add(date1, 2, i + 1);
            gridPaneValue.add(type1, 3, i + 1);
        }
    }

    private void styleLabel(Label label) {
        label.setStyle("-fx-font-size: 18px; -fx-alignment: center;");
        label.setMaxWidth(Double.MAX_VALUE); // pozwala się rozciągać w komórce
        GridPane.setHalignment(label, javafx.geometry.HPos.CENTER); // poziome wyśrodkowanie
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
        incomsViewController.setAllGridPanes();//need be this here incomsViewController. ....

    }

    private Income getDataAboutIncome(){
        Income income = new Income();

        income.date = dateField.getText().trim();
        income.price = Float.valueOf(priceField.getText().trim());
        income.type = typeField.getText().trim();
        income.userId = user.id;//need this !!

        return income;
    }


    private void setAllGridPanes(){
        //setting stuff
        setTextOnGridPaneTop3Incomes();

    }

    public void setUser (User user){
        this.user = user;
    }



}