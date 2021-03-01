package cwomack.a5;

import cwomack.a6.Coin;
import cwomack.a6.CoinGecko;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChartController {
    @FXML
    ImageView btnBackArrow;

    @FXML
    LineChart<Number, Number> priceChart;

    @FXML
    ComboBox cbDataRange;

    @FXML
    ComboBox cbCoinSelector;

    Coin bitcoin, ethereum;

    /**
     * Function to initialize the price range of each coin for the last 365 days.
     */
    public void initialize() {
        priceChart.setAnimated(false);
        bitcoin = new Coin("bitcoin");
        ethereum = new Coin("ethereum");

        CoinGecko.updatePriceHistory(bitcoin, 365);
        CoinGecko.updatePriceHistory(ethereum, 365);
        priceChart.getData().add(bitcoin.getHistoricalValues());
    }

    /**
     * Function that returns the user to the home page when the back arrow is clicked with a mouse.
     * @param mouseEvent default mouseEvent is a mouse click.
     * @throws IOException
     */
    public void onBackArrowClicked(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Details.fxml"));
        Stage primaryStage = (Stage) btnBackArrow.getScene().getWindow();
        primaryStage.setScene(new Scene(root, 700, 475));
    }

    /**
     * Function that sets the number of days when the ComboBox value is changed within the application.
     * @param actionEvent when the ComboBox value changes.
     */
    public void onDateRangeChanged(ActionEvent actionEvent) {
        int days = 0;
        switch((String)cbDataRange.getValue()){
            case "Year":
                days = 365;
                break;
            case "90 Days":
                days = 90;
                break;
            case "60 Days":
                days = 60;
                break;
            case "30 Days":
                days = 30;
                break;
            case "Week":
                days = 7;
                break;
        }
        CoinGecko.updatePriceHistory(bitcoin, days);
        CoinGecko.updatePriceHistory(ethereum, days);
    }

    /**
     * Function that updates the price chart based on the Coin ComboBox selection
     */
    private void updateChart(){
        priceChart.getData().clear();
        switch((String) cbCoinSelector.getValue()){
            case "Bitcoin":
                priceChart.getData().add(bitcoin.getHistoricalValues());
                break;
            case "Ethereum":
                priceChart.getData().add(ethereum.getHistoricalValues());
                break;
            case "All":
                priceChart.getData().add(bitcoin.getHistoricalValues());
                priceChart.getData().add(ethereum.getHistoricalValues());
                break;
        }
    }

    /**
     * Simple function that clears and updates the chart when the ComboBox events are changed.
     * @param actionEvent
     */
    public void onCoinSelectionChanged(ActionEvent actionEvent) {
        updateChart();
    }
}
