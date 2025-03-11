package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IncomsView extends BaseUserView{
    public IncomsView(){
        super();
        System.out.println("Incoms View loaded");
    }

    public void switchToIncomsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(View.FinanceApplication.class.getResource("Incoms.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow(); // Pobierz aktualne okno
        Scene scene = new Scene(root);
        stage.setTitle("---Incoms View---");
        stage.setScene(scene);
        stage.show();
    }

}
