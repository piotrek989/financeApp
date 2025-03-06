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

    public void switchToUserView(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserView.fxml"));
        root = loader.load();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
