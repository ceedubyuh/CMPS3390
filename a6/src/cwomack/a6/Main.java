package cwomack.a6;

public class Main {
	/**
	 * Main driver for setting and updating the prices of each coin.
	 * @param args
	 */
    public static void main(String[] args) {
	    Coin bitcoin = new Coin("bitcoin");
        Coin ethereum = new Coin("ethereum");
	    CoinGecko.updateCurrentPrice(bitcoin);
	    CoinGecko.updateCurrentPrice(ethereum);

	    CoinGecko.updatePriceHistory(bitcoin, 7);

	    System.out.println(bitcoin);
	    System.out.println(ethereum);
    }
}
