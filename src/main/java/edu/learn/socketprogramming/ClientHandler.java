package edu.learn.socketprogramming;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread{
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private BufferedReader bufferedReader;
    private Socket socket;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        startConversation();
    }

    public boolean verifyClient(List<String> clientNames) {
        try {
            dataOutputStream.writeUTF("Please Enter your name :");
            String clientName = dataInputStream.readUTF();
            if (clientNames.contains(clientName)) {
                System.out.println("Client already exists");
                dataOutputStream.writeBoolean(false);
                dataOutputStream.writeUTF("Client Name already Exists");
                dataOutputStream.flush();
                return false;
            } else {
                System.out.println("Client Verification Successful");
                dataOutputStream.writeBoolean(true);
                dataOutputStream.writeUTF("Client verified , You have successfully connected to the server ");
                dataOutputStream.flush();
                clientNames.add(clientName);
                System.out.println("Staring conversation with client " + clientName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void startConversation() {
        String serverText = "";
        ReadMessage readMessage = new ReadMessage(dataInputStream, socket, "Client");
        readMessage.start();
        while(readMessage.isAlive()) {
            try {
                serverText = bufferedReader.readLine();
                dataOutputStream.writeUTF(serverText);
                dataOutputStream.flush();
            } catch (IOException e) {
                System.out.println("Error, Client connection broke abruptly");
                e.printStackTrace();
                break;
            }
        }
    }


}
