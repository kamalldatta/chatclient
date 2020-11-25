package edu.learn.socketprogramming;

public class ClientInstance {

    public static void main(String[] args) {
        Client client = new Client();
        if(client.verification()) {
            client.startConversation();
        }
    }
}
