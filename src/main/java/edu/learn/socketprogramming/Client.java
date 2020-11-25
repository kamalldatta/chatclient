package edu.learn.socketprogramming;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private String clientName;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private BufferedReader bufferedReader;

    public Client() {
        establishConnection("localhost", 6666);
        initInputOutputStreams();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void establishConnection(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initInputOutputStreams() {
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean verification() {
        boolean isVerified = false;
        String serverEntryMessage = "";
        String verificationMessage = "";
        try {
            serverEntryMessage = dataInputStream.readUTF();
            System.out.println(serverEntryMessage);
            clientName = bufferedReader.readLine();
            dataOutputStream.writeUTF(clientName);
            dataOutputStream.flush();
            isVerified = dataInputStream.readBoolean();
            verificationMessage = dataInputStream.readUTF();
            System.out.println(verificationMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isVerified;
    }

    public void startConversation() {
        String clientText = "";
        ReadMessage readMessage = new ReadMessage(dataInputStream, socket, "Server");
        readMessage.start();
        while (readMessage.isAlive()) {
            try {
                clientText = bufferedReader.readLine();
                dataOutputStream.writeUTF(clientText);
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error Occured, connection to server broken");
                break;
            }
        }
    }
}
