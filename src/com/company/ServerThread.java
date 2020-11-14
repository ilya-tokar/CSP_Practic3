package com.company;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerThread extends Thread{
    private Socket socket = null;
    private ConcurrentHashMap<String, Task> clients = null;

    public ServerThread(Socket socket, ConcurrentHashMap<String, Task> tasks) {
        this.socket = socket;
        this.clients = tasks;
    }

    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String InputString;
            while ((InputString = in.readLine()) != null){
                System.out.println(InputString);
                clients.put(String.valueOf(getId()), new Task(out,in,socket,InputString));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
