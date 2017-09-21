package com.Chat_Client.www;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class ChatClient implements Runnable {
    private static Socket socket;
    private static final int PORT = 5555;
    private static final String HOST = "localhost";
    private static boolean close = true;

    public static void main(String[] args) {
        try {
            socket = new Socket(HOST, PORT);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            new Thread(new ChatClient()).start();
            while(close){
                printStream.println(bufferedReader.readLine());
            }
            dataInputStream.close();
            printStream.close();
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    public void run(){
        String string;
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            while ((string = dataInputStream.readLine()) != null){
                System.out.println(string);
            }
            close = false;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
