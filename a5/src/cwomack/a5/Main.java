package cwomack.a5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * Primary main method to create the app window.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
        Parent root = loader.load();
        DetailsController controller = loader.getController();
        Scene scene = new Scene(root, 700,475);
        primaryStage.setTitle("Coin Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnHidden(e -> controller.shutdown());
    }



    public static void main(String[] args) {
        launch(args);
    }
}
