package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FinanceApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.FinanceApplication.class.getResource("LogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        LoginUI loginUIController = fxmlLoader.getController();//dodane
        loginUIController.setStage(stage);//podanie stage w argumencie

        stage.setTitle("Logging");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}