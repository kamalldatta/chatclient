package edu.learn.socketprogramming;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT_U = 6666;
    public static void main(String[] args) {
        List<ClientHandler> clients = new ArrayList<>();
        List<String> clientNames = new ArrayList<>();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT_U);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("New client trying to  connect : " + socket);
                System.out.println("Assigning new thread to client");
                ClientHandler clientHandler = new ClientHandler(socket);
                if(clientHandler.verifyClient(clientNames)) {
                    clients.add(clientHandler);
                    clientHandler.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addNewClient() {

    }
}
