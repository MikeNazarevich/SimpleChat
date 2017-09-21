package com.Chat_Server.www;


import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiChatServer {
   private static final int PORT = 5555;
   private static Socket socket;
   private static final int maxClients = 10;
   private static ForClient[] clients = new ForClient[maxClients];

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true){
                socket = serverSocket.accept();

                int i;
                for (i = 0; i < maxClients; i++){
                    if (clients[i] == null){
                        clients[i] = new ForClient(socket, clients);
                        clients[i].start();
                        break;
                    }
                }

                if (i == maxClients){
                    PrintStream printStream = new PrintStream(socket.getOutputStream());
                    System.out.println("Too much clients");
                    printStream.println("Too much clients");
                    printStream.close();
                    serverSocket.close();
                    break;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


































/*

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true){
                Socket socket = serverSocket.accept();

                int i;
                for (i = 0; i < maxClients; i++){
                    if (threads[i] == null){
                        threads[i] = new ForClient(socket, threads);
                        threads[i].start();
                        break;
                    }
                }

                if (i == maxClients){
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF("Server busy");
                    dataOutputStream.close();
                    socket.close();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
 */