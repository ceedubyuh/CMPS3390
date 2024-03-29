package cwomack.a6;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CoinGecko {
    /**
     * Initializes the HttpClient and HTTP version
     */
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    /**
     * Function to update the price history of each coin based on the amount of days specified
     * @param coin which coin is selected, bitcoin or ethereum
     * @param days number of days selected
     */
    public static void updatePriceHistory(Coin coin, int days) {
        coin.getHistoricalValues().getData().clear();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.coingecko.com/api/v3/coins/"
                        + coin.getName() + "/market_chart?vs_currency=usd&days="
                        + days + "&interval=daily"))
                .setHeader("User-Agent", "Java 11 HttpClient cwomack.a6")
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject obj = new JSONObject(response.body());
            JSONArray priceArray = obj.getJSONArray("prices");
            for(int i = 0; i<priceArray.length(); i++){
                JSONArray temp = priceArray.getJSONArray(i);
                double tmpVal = temp.getDouble(1);
                coin.addHistoricalValue(i - priceArray.length() + 1, tmpVal);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to update the current price of either coin
     * @param coin which coin is selected, bitcoin or ethereum
     */
    public static void updateCurrentPrice(Coin coin){
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.coingecko.com/api/v3/simple/price?ids=" + coin.getName() + "&vs_currencies=usd"))
                .setHeader("User-Agent", "Java 11 HttpClient cwomack.a6")
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());
            double value = jsonObject.getJSONObject(coin.getName()).getDouble("usd");
            coin.setCurrentPrice(value);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
