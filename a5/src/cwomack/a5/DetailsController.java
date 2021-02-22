package cwomack.a5;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailsController {
    @FXML
    Label labBTCValue;

    @FXML
    Label labETHValue;

    @FXML
    HBox btHBox;

    @FXML
    HBox etHBox;


    public void initialize(){
        labBTCValue.setText("$56,682.10");
        labETHValue.setText("S1,905.22");
    }


    public void onDetailButtonClicked(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getSource() == btHBox){
            Parent root = FXMLLoader.load(getClass().getResource("BTC.fxml"));
            Stage primaryStage = (Stage) btHBox.getScene().getWindow();
            primaryStage.setScene(new Scene(root, 700, 475));
        }
        if(mouseEvent.getSource() == etHBox){
            Parent root = FXMLLoader.load(getClass().getResource("ETH.fxml"));
            Stage primaryStage = (Stage) btHBox.getScene().getWindow();
            primaryStage.setScene(new Scene(root, 700, 475));
        }
    }
}
