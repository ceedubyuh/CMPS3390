package cwomack.a6;

public class UpdateCoinTimerTask implements Runnable {
    private Coin coin;

    public UpdateCoinTimerTask(Coin coin) {
        this.coin = coin;
    }

    /**
     * Override function that gives console output to check if prices are updating and if prices have changed recently.
     */
    @Override
    public void run() {
        System.out.println("Checking for update on " + coin.getName());
        double currValue = this.coin.getCurrentPrice();
        CoinGecko.updateCurrentPrice(this.coin);
        if(currValue != this.coin.getCurrentPrice()){
            System.out.println("---------- Price Changed " + this.coin.getName()
            + " " + currValue + " --> " + this.coin.getCurrentPrice());
        }
    }
}
