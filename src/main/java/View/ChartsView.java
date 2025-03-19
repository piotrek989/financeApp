package View;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChartsView {
    public ChartsView(){
        System.out.println("Charts View loaded");
    }

    public void switchToChartsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(View.FinanceApplication.class.getResource("Charts.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow(); // Pobierz aktualne okno
        Scene scene = new Scene(root);
        stage.setTitle("---Charts View---");
        stage.setScene(scene);
    }

}
