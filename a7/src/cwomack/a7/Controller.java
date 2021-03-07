package cwomack.a7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import jforsythe.Message;
import jforsythe.MessageType;

import java.io.*;
import java.net.Socket;

public class Controller {
    @FXML
    TextField textInput;

    @FXML
    TextArea textOutput;

    //TODO textMembers area
    @FXML
    TextArea textMembers;

    private String name;
    private Socket socket;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;

    /**
     * Main initialize function to connect to the server.
     * Will not create a socket until a name is given and will throw an exception if the socket cannot reach
     * the server.
     * @throws IOException
     */
    public void initialize() throws IOException {
        //Stop the program and wait for user to enter their name.
        TextInputDialog nameInput = new TextInputDialog("What is your name?");
        nameInput.setHeaderText("Welcome to Harmony!");
        nameInput.showAndWait();
        name = nameInput.getResult();

        //Create a socket if and only if the user entered a name.
        socket = new Socket("odin.cs.csub.edu", 3390);
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.flush();

        ServerListener serverListener = new ServerListener(this.socket, this);
        serverListener.start();

        //Display message if socket was able to connect.
        Message tmp = new Message(MessageType.CONNECT, name, "Hello!");
        objectOutputStream.writeObject(tmp);
        objectOutputStream.flush();
    }

    /**
     * Function to that sends the inputted message to the server and clears the text box for another message.
     * @param actionEvent
     * @throws IOException
     */
    public void onInputAction(ActionEvent actionEvent) throws IOException {
        Message tmp = new Message(MessageType.MESSAGE, name, textInput.getText());
        textInput.clear();
        objectOutputStream.writeObject(tmp);
        objectOutputStream.flush();

    }
    /**
     * Function to close out of output streams when app is closed.
     * @throws IOException thrown if the outputs cannot be closed.
     */
    public void exit() throws IOException {
        objectOutputStream.close();
        outputStream.close();
        socket.close();
    }

    /**
     * Function to append the message text to a string
     * @param s
     */
    public void addMessage(String s) {
        textOutput.appendText(s);
    }
}
