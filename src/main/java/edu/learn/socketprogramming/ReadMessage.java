package edu.learn.socketprogramming;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadMessage extends Thread{

    private DataInputStream dataInputStream;
    private Socket socket;
    private String agent;

    public  ReadMessage(DataInputStream dataInputStream, Socket socket, String agent) {
        this.dataInputStream = dataInputStream;
        this.socket = socket;
        this.agent = agent;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String incommingMessage = dataInputStream.readUTF();
                System.out.println(agent + " Message: " + incommingMessage);
                if (incommingMessage.equals("stop")) {
                    System.out.println(agent + " " + socket.toString() + " exits ");
                    socket.close();
                    break;
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
                System.out.println("Connection Ended Abruptly");
                break;
            }
        }
    }
}
