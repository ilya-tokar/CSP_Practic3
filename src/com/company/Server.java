package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server {

    public static void main(String[] args) throws IOException {

        if(args.length == 0){
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(0);
        }

        final Map<SocketChannel, ByteBuffer> sockets = new ConcurrentHashMap<>();
        int Port = Integer.parseInt(args[0]);

        final ServerSocketChannel serverSocket = ServerSocketChannel.open();
        // Binding this server on the port
        serverSocket.bind(new InetSocketAddress(Port));
        serverSocket.configureBlocking(false); // Make Server nonBlocking

        RunnerTaskThread runnerTaskThread = new RunnerTaskThread((ConcurrentHashMap<SocketChannel, ByteBuffer>) sockets);
        runnerTaskThread.start();

        while (true) {

            final SocketChannel socket = serverSocket.accept();

            if(socket != null){
               socket.configureBlocking(false);
               sockets.put(socket, ByteBuffer.allocate(30));
            }

            sockets.keySet().removeIf((socketChannel) -> !socketChannel.isOpen());
            // Remove socketChannel which is not opened

        }
    }
}
