package View;

import Model.Expense;
import Model.Income;
import Model.User;
import Presenter.PresenterFacade;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChartsView {

    public ChartsView() {
        System.out.println("Charts View loaded");
    }

    public ChartsView(User user) {
        this.user = user;
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
    Button buttonReloadData;
    //charts
    @FXML
    ScatterChart incomsChart;

    @FXML
    ScatterChart expensesChart;

    @FXML
    PieChart circleChart;

    @FXML
    LineChart InExChart;

    public void switchToChartsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(View.FinanceApplication.class.getResource("Charts.fxml"));

        Parent root = loader.load();

        ChartsView chartsViewController = loader.getController();

        chartsViewController.setUser(user);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        chartsViewController.setUserLoginOnLabel();

        chartsViewController.getIncomesAndExpenses();//need this to load Charts it is responsible for that
        chartsViewController.getCircleChartFullFilled();
        chartsViewController.getInExChartFullFilled();
    }

    private void reqFocusPane() {
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
        var IncomesVIew = new IncomsView(user);
        IncomesVIew.switchToIncomsView(event);
        reqFocusPane();
    }

    @FXML
    public void handlebtnExpenses(ActionEvent event) throws IOException {
        var ExpensesView = new ExpensesView(user);//here need user bacuse error of user == null
        ExpensesView.switchToExpensesView(event);
        reqFocusPane();
    }

    @FXML
    public void handlebtnCharts(ActionEvent event) throws IOException {
        switchToChartsView(event);
        reqFocusPane();
    }

    @FXML
    public void setUserLoginOnLabel() {//this metod is inherited because public
        String login = user.login;
        this.userNameLabel.setText("Welcome " + login);


    }

    @FXML
    public void handleReloadButton(ActionEvent event) throws Exception {
        switchToChartsView(event);
    }


    // Metoda pomocnicza do wyświetlania alertów
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void getIncomesAndExpenses(){
        PresenterFacade presenterFacade = new PresenterFacade();
        ArrayList<Income> incomes = presenterFacade.getUserIncomes(user.id);
        ArrayList<Expense> expenses = presenterFacade.getUserExpenses(user.id);

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Incomes");

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Expenses");

        for (Income income : incomes) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(income.date, income.price);
            incomeSeries.getData().add(data);

            Tooltip tooltip = new Tooltip(income.type);
            Tooltip.install(data.getNode(), tooltip);
        }

        for (Expense expense : expenses) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(expense.date, expense.price);
            expenseSeries.getData().add(data);

            Tooltip tooltip = new Tooltip(expense.type);
            Tooltip.install(data.getNode(), tooltip);
        }

        incomsChart.getData().clear();
        incomsChart.getData().add(incomeSeries);

        expensesChart.getData().clear();
        expensesChart.getData().add(expenseSeries);

        CategoryAxis incomesXAxis = (CategoryAxis) incomsChart.getXAxis();
        NumberAxis incomesYAxis = (NumberAxis) incomsChart.getYAxis();
        incomesYAxis.setLabel("Amount (PLN)");
        incomesXAxis.setTickLabelRotation(90);
        incomesXAxis.setTickLabelGap(1);
        incomesXAxis.setTickLabelsVisible(true);
        incomesXAxis.setAutoRanging(false);
        incomesXAxis.setCategories(FXCollections.observableArrayList(
                incomes.stream().map(income -> income.date).collect(Collectors.toList())
        ));


        CategoryAxis expensesXAxis = (CategoryAxis) expensesChart.getXAxis();
        NumberAxis expensesYAxis = (NumberAxis) expensesChart.getYAxis();
        expensesYAxis.setLabel("Amount (PLN)");
        expensesXAxis.setTickLabelRotation(90);
        expensesXAxis.setTickLabelGap(1);
        expensesXAxis.setTickLabelsVisible(true);
        expensesXAxis.setAutoRanging(false);
        expensesXAxis.setCategories(FXCollections.observableArrayList(
                expenses.stream().map(expense -> expense.date).collect(Collectors.toList())
        ));

        Platform.runLater(() -> {
            for (XYChart.Data<String, Number> data : expenseSeries.getData()) {
                Tooltip tooltip = new Tooltip(getExpenseTypeByDate(expenses, data.getXValue(), data.getYValue().floatValue()));
                Tooltip.install(data.getNode(), tooltip);
            }
            for (XYChart.Data<String, Number> data : incomeSeries.getData()) {
                Tooltip tooltip = new Tooltip(getIncomeTypeByDate(incomes, data.getXValue(), data.getYValue().floatValue()));
                Tooltip.install(data.getNode(), tooltip);
            }
        });
        incomsChart.setTitle("      Incomes in days");
        expensesChart.setTitle("      Expenses in days");
    }

    private String getExpenseTypeByDate(ArrayList<Expense> expenses, String date, float price) {
        for (Expense e : expenses) {
            if (e.date.equals(date) && e.price == price) {
                return e.type;
            }
        }
        return "Brak danych";
    }

    private String getIncomeTypeByDate(ArrayList<Income> incomes, String date, float price) {
        for (Income i : incomes) {
            if (i.date.equals(date) && i.price == price) {
                return i.type;
            }
        }
        return "Brak danych";
    }

    public void getCircleChartFullFilled(){
        PresenterFacade presenterFacade = new PresenterFacade();
        ArrayList<Income> incomes = presenterFacade.getUserIncomes(user.id);
        ArrayList<Expense> expenses = presenterFacade.getUserExpenses(user.id);

        float totalIncome = 0;
        for (Income income : incomes) {
            totalIncome += income.price;
        }

        float totalExpense = 0;
        for (Expense expense : expenses) {
            totalExpense += expense.price;
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Incomes", totalIncome),
                new PieChart.Data("Expenses", totalExpense)
        );

        circleChart.setData(pieChartData);
        circleChart.setTitle("Total Incoms and Expenses");
    }

    public void getInExChartFullFilled() throws IOException {
        PresenterFacade presenterFacade = new PresenterFacade();
        ArrayList<Income> incomes = presenterFacade.getUserIncomes(user.id);
        ArrayList<Expense> expenses = presenterFacade.getUserExpenses(user.id);

        // Zbierz wszystkie daty, w których coś się wydarzyło
        TreeSet<String> allDates = new TreeSet<>();
        for (Income income : incomes) {
            allDates.add(income.date);
        }
        for (Expense expense : expenses) {
            allDates.add(expense.date);
        }

        // Posortowane daty jako lista
        List<String> sortedDates = new ArrayList<>(allDates);

        // Mapy: data -> suma przychodów/wydatków danego dnia
        Map<String, Float> incomeMap = new HashMap<>();
        for (Income income : incomes) {
            incomeMap.put(income.date,
                    incomeMap.getOrDefault(income.date, 0f) + income.price);
        }

        Map<String, Float> expenseMap = new HashMap<>();
        for (Expense expense : expenses) {
            expenseMap.put(expense.date,
                    expenseMap.getOrDefault(expense.date, 0f) + expense.price);
        }

        // Skumulowane sumy
        float cumulativeIncome = 0;
        float cumulativeExpense = 0;

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Cumulative Income");

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Cumulative Expense");

        for (String date : sortedDates) {
            if (incomeMap.containsKey(date)) {
                cumulativeIncome += incomeMap.get(date);
                incomeSeries.getData().add(new XYChart.Data<>(date, cumulativeIncome));
            }

            if (expenseMap.containsKey(date)) {
                cumulativeExpense += expenseMap.get(date);
                expenseSeries.getData().add(new XYChart.Data<>(date, cumulativeExpense));
            }
        }

        InExChart.getData().clear();
        InExChart.getData().addAll(incomeSeries, expenseSeries);

        // Ustawienia osi X
        CategoryAxis xAxis = (CategoryAxis) InExChart.getXAxis();
        xAxis.setTickLabelRotation(90);
        xAxis.setTickLabelGap(1);
        xAxis.setTickLabelsVisible(true);
        xAxis.setAutoRanging(false);
        xAxis.setCategories(FXCollections.observableArrayList(sortedDates));

        // Etykieta osi Y
        NumberAxis yAxis = (NumberAxis) InExChart.getYAxis();
        yAxis.setLabel("Amount (PLN)");
        InExChart.setTitle("        Comparison Incoms vs Expenses");
    }







    public void setUser(User user) {
        this.user = user;
    }

}
