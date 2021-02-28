package cwomack.a5;

import cwomack.a6.Coin;
import cwomack.a6.UpdateCoinTimerTask;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class DetailsController {
    @FXML
    Label labBTCValue;

    @FXML
    Label labETHValue;

    @FXML
    HBox btHBox;

    @FXML
    HBox etHBox;

    Coin bitcoin, ethereum;
    Timer bitcoinTimer, ethereumTimer;

    public void initialize() {
        this.bitcoin = new Coin("bitcoin");
        this.ethereum = new Coin("ethereum");

        labBTCValue.textProperty().bind(Bindings.format("$%-10.2f", bitcoin.currentPriceProperty()));
        labETHValue.textProperty().bind(Bindings.format("$%-10.2f", ethereum.currentPriceProperty()));
        bitcoinTimer = new Timer();
        bitcoinTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new UpdateCoinTimerTask(bitcoin));
            }
        }, 0, 5000);
        ethereumTimer = new Timer();
        ethereumTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new UpdateCoinTimerTask(ethereum));
            }
        }, 0, 5000);
    }

    public void onDetailButtonClicked(MouseEvent mouseEvent) throws IOException {
        shutdown();
        Parent root = FXMLLoader.load(getClass().getResource("Chart.fxml"));
        Stage primaryStage = (Stage) btHBox.getScene().getWindow();
        primaryStage.setScene(new Scene(root, 700, 475));
    }
    public void shutdown(){
        System.out.println("Shutdown called, stopping timers.");
        bitcoinTimer.cancel();
        ethereumTimer.cancel();
    }
}
