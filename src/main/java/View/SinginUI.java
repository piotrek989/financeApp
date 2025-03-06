package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SinginUI {
    private Scene scene;
    private Parent root;

    public void switchToSingInView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SingIn.fxml"));
        root = loader.load();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
