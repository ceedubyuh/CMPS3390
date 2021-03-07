package cwomack.chatclient;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {

        try {
            Thread.sleep(Integer.parseInt(args[0]));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Socket socket = new Socket("localhost", 3390);
        socket.close();
    }

}
