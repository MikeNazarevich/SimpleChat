package com.Chat_Server.www;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;



class ForClient extends Thread {
    private Socket socket;
    private DataInputStream dataInputStream;
    private PrintStream printStream;
    private final ForClient[] clients;
    private int maxClients;

    public ForClient(Socket socket, ForClient[] threads) {
        this.socket = socket;
        this.clients = threads;
        maxClients = threads.length;
    }

    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());

            printStream.println("Enter your name: ");
            String name = dataInputStream.readLine();

            System.out.println("connect: " + name);
            printStream.println("Hello " + name);

            for (int i = 0; i < maxClients; i++) {
                if (clients[i] != null) {
                    clients[i].printStream.println("A new user " + name);
                }
            }
            while (true) {
                String line = dataInputStream.readLine();
                System.out.println(name + ": " + line);

                if (line.equals("quit")) {
                    printStream.println("Disconnect " + name);
                    break;
                }

                for (int i = 0; i < maxClients; i++) {
                    if (clients[i] != null) {
                        clients[i].printStream.println(name + ": " + line);
                    }
                }
            }

            dataInputStream.close();
            printStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
