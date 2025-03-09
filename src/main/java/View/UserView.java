package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class UserView {
    private Scene scene;
    private Parent root;

    public void switchToUserView(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow(); // Pobierz aktualne okno
        stage.setTitle("User panel");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); // Pokaż nową scenę
    }
}
