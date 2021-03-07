package cwomack.chatclient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket((3390));
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client Connected");

        clientSocket.close();
        serverSocket.close();
    }
}
