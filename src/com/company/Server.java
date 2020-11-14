package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;


public class Server {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Task> tasks = new ConcurrentHashMap<String, Task>();

        if(args.length != 1){
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int Port = Integer.parseInt(args[0]);

        RunnerTaskThread runnerTaskThread = new RunnerTaskThread(tasks);
        runnerTaskThread.start();
        ResultTaskThread resultTaskThread = new ResultTaskThread(tasks);
        resultTaskThread.start();

        try(
                ServerSocket serverSocket = new ServerSocket(Port);
        ) {
            while (true) {
                ServerThread t = new ServerThread(serverSocket.accept(), tasks);
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
