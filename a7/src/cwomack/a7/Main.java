package cwomack.a7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main function for JavaFX based server chat client.
 * Very lite Discord spin-off called "Harmony" for memes.
 * @author Carter Womack
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Override function to load the FXML application and set its window size.
     * @param primaryStage
     * @throws Exception thrown when the loader cannot load.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root, 600,400);
        primaryStage.setTitle("Harmony");
        primaryStage.setScene(scene);
        primaryStage.setOnHiding(e->{
            try {
                controller.exit();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
