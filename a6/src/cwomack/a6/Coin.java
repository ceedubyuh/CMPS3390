package cwomack.a6;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.chart.XYChart;

public class Coin {
    private String name;
    private final DoubleProperty currentPrice;
    private  XYChart.Series<Number, Number> historicalValues;

    /**
     * Function that initializes the price chart values.
     * @param name name of the coin being initialized.
     */
    public Coin(String name) {
        historicalValues = new XYChart.Series<>();
        historicalValues.setName(name);
        currentPrice = new SimpleDoubleProperty();
        this.name = name;
    }

    /**
     * Getter for the HistoricalValues
     * @return historicalValues of each coin.
     */
    public XYChart.Series<Number, Number> getHistoricalValues() {
        return historicalValues;
    }

    /**
     * Setter for the HistoricalValues
     * @param historicalValues
     */
    public void setHistoricalValues(XYChart.Series<Number, Number> historicalValues) {
        this.historicalValues = historicalValues;
    }

    /**
     * Function that adds each value to the chat
     * @param day number of days specified
     * @param value value of each coin at each day
     */
    public void addHistoricalValue(int day, double value){
        historicalValues.getData().add(new XYChart.Data<>(day, value));
    }

    /**
     * Getter function to get the name of the coin specified
     * @return name of the coin
     */
    public String getName() {
        return name;
    }

    /**
     * Setter function to set the name of the coin
     * @param name name of the coin specified
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter function to get the current price of each coin from the REST service
     * @return currentPrice
     */
    public double getCurrentPrice() {
        return currentPrice.get();
    }

    public DoubleProperty currentPriceProperty() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice.set(currentPrice);
    }

    /**
     * ToString function to print the real time value of each coin
     * @return Price of each coin as a string
     */
    @Override
    public String toString(){
        return String.format("%20s: $%-10.2f", this.name, this.currentPrice.getValue());
    }
}
