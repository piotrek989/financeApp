package View;

import Model.Income;
import Model.User;
import Presenter.PresenterFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

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

    @FXML
    AnchorPane middleAnchor;

    @FXML
    Button buttonReloadData;

    @FXML
    Button loadCSVBtn;



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
        var ExpensesView = new ExpensesView(user);//here need user bacuse error of user == null
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
    public void handleLoadCSVBtn(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            ArrayList<Income> loadedIncomes = new ArrayList<>();
            Pattern datePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"); // yyyy-MM-dd

            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                int lineNumber = 0;

                while ((line = br.readLine()) != null) {
                    lineNumber++;
                    String[] parts = line.split(";");
                    if (parts.length != 3) {
                        showAlert("Info", "Error on line " + lineNumber + ": Incorrect number of fields.");
                        return;
                    }

                    String date = parts[0].trim();
                    String priceStr = parts[1].trim();
                    String type = parts[2].trim();

                    if (!datePattern.matcher(date).matches()) {
                        showAlert("Info", "Error on line " + lineNumber + ": Invalid date format. Expected YYYY-MM-DD.");
                        return;
                    }

                    try {
                        float price = Float.parseFloat(priceStr);//price tpyu float
                        loadedIncomes.add(new Income(date, price, type, user.id));
                    } catch (NumberFormatException e) {
                        showAlert("Info", "Error on line " + lineNumber + ": Price is not a valid number.");
                        return;
                    }
                }
                PresenterFacade facade = new PresenterFacade();
                facade.addIncoms(loadedIncomes);//adding all lines from csv file

                System.out.println("Loaded " + loadedIncomes.size() + " incomes");
            }
        }
    }


    @FXML
    public void handleReloadButton(ActionEvent event) throws Exception{
        switchToIncomsView(event);
    }

    @FXML
    public void handleButtonAddItem(){
        boolean validationOfFieldIncoms = validateFieldsOfIncome();
        if (validationOfFieldIncoms) {
            Income income = getDataAboutIncome();//gettin info from fields
            boolean innerVal = innerValidation(income);//checking those format of each field (price>0, date corr format)
            if (innerVal) {
                var PresenterFacade = new PresenterFacade();

                if (!PresenterFacade.addIncome(income))
                    showAlert("Inncorect format", "Not added to database");

            }

        }
    }

    public boolean validateFieldsOfIncome() {
        if (dateField == null || dateField.getText().trim().isEmpty()) {
            showAlert("Invalid Date", "date field cannot be empty.");
            return false;
        }

        if (priceField == null || priceField.getText().trim().isEmpty()) {
            showAlert("Invalid Amount", "price field cannot be empty.");
            return false;
        }

        if (typeField == null || typeField.getText().trim().isEmpty()) {//check if is not null and if fulfilled
            showAlert("Invalid Type", "type field cannot be empty.");
            return false;
        }

        return true;
    }

    public boolean innerValidation(Income income) {
        if (!income.date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            showAlert("Invalid Date Format", "Date must be in the format YYYY-MM-DD (e.g. 2025-04-12).");
            return false;
        }

        if (income.price <= 0) {
            showAlert("Invalid Amount", "Amount must be a number greater than zero.");
            return false;
        }

        return true;

    }

    // Metoda pomocnicza do wyświetlania alertów
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void setTextOnGridPaneTop3Incomes() {
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

    private void styleLabelBolded(Label label) {
        label.setStyle("-fx-font-size: 18px; -fx-alignment: center; -fx-font-weight: bold;");
        label.setMaxWidth(Double.MAX_VALUE); // pozwala się rozciągać w komórce
        GridPane.setHalignment(label, javafx.geometry.HPos.CENTER); // poziome wyśrodkowanie
    }

    private void styleDeleteButton(Button btn) {
        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/logo/delete_bin.png")));
        deleteIcon.setFitWidth(20);
        deleteIcon.setFitHeight(20);

        btn.setGraphic(deleteIcon);
        btn.setPrefSize(40, 40);

        GridPane.setHalignment(btn, HPos.CENTER);
        GridPane.setValignment(btn, VPos.CENTER);
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
        setGridPaneAllIncoms();

    }

    private void setGridPaneAllIncoms() {
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPaneAllIncoms = new GridPane();

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(10);
        gridPaneAllIncoms.getColumnConstraints().add(col1);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        gridPaneAllIncoms.getColumnConstraints().add(col2);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        gridPaneAllIncoms.getColumnConstraints().add(col3);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(30);
        gridPaneAllIncoms.getColumnConstraints().add(col4);

        ColumnConstraints col5 = new ColumnConstraints();//deletion of income
        col5.setPercentWidth(10);
        gridPaneAllIncoms.getColumnConstraints().add(col5);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPrefHeight(65);
        gridPaneAllIncoms.getRowConstraints().add(rowConstraints);

        Label noLabel = new Label("No.");
        Label priceLabel = new Label("Price");
        Label dateLabel = new Label("Date");
        Label typeLabel = new Label("Type");
        Label deleteLabel = new Label("Deletion");

        styleLabelBolded(noLabel);
        styleLabelBolded(priceLabel);
        styleLabelBolded(dateLabel);
        styleLabelBolded(typeLabel);
        styleLabelBolded(deleteLabel);


        gridPaneAllIncoms.add(noLabel, 0, 0);
        gridPaneAllIncoms.add(priceLabel, 1, 0);
        gridPaneAllIncoms.add(dateLabel, 2, 0);
        gridPaneAllIncoms.add(typeLabel, 3, 0);
        gridPaneAllIncoms.add(deleteLabel, 4, 0);

        PresenterFacade presenterFacade = new PresenterFacade();
        ArrayList<Income> incomes = presenterFacade.getUserIncomes(user.id);//gettin info about user
        user.incomes = incomes;//setting incomes of our userxD
        int incomesCount = incomes.size();

        for (int i = 0; i < incomesCount; i++) {
            if (i + 1 >= gridPaneAllIncoms.getRowCount()) {
                RowConstraints row = new RowConstraints();
                row.setPrefHeight(65);
                gridPaneAllIncoms.getRowConstraints().add(row);
            }

            Label no = new Label(String.valueOf(i + 1));
            Label price1 = new Label(String.valueOf(incomes.get(i).price));
            Label date1 = new Label(String.valueOf(incomes.get(i).date));
            Label type1 = new Label(String.valueOf(incomes.get(i).type));
            ButtonWithId deleteButton = new ButtonWithId(user.incomes.get(i).id);//it's id on an income

            styleLabel(no);
            styleLabel(price1);
            styleLabel(date1);
            styleLabel(type1);
            styleDeleteButton(deleteButton);

            gridPaneAllIncoms.add(no, 0, i + 1);
            gridPaneAllIncoms.add(price1, 1, i + 1);
            gridPaneAllIncoms.add(date1, 2, i + 1);
            gridPaneAllIncoms.add(type1, 3, i + 1);
            gridPaneAllIncoms.add(deleteButton, 4, i + 1);


        }

        gridPaneAllIncoms.setStyle("-fx-background-color: lightgrey;");
        scrollPane.setContent(gridPaneAllIncoms);
        scrollPane.setFitToWidth(true);

        AnchorPane.setTopAnchor(scrollPane, 450.0);
        AnchorPane.setLeftAnchor(scrollPane, 50.0);
        AnchorPane.setRightAnchor(scrollPane, 50.0);
        AnchorPane.setBottomAnchor(scrollPane, 50.0);

        middleAnchor.getChildren().add(scrollPane);
    }


    public void setUser (User user){
        this.user = user;
    }



}