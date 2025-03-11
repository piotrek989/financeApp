package View;

import Model.Expense;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExpensesView extends BaseUserView{
    public ExpensesView(){
        super();
        System.out.println("Expenses View loaded");
    }

public void switchToExpensesView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(View.FinanceApplication.class.getResource("Expenses.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow(); // Pobierz aktualne okno
        Scene scene = new Scene(root);
        stage.setTitle("---Expenses View---");
        stage.setScene(scene);
    }

}
