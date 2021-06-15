package liubei.juc.socket_thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadServerSocket {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true){
                final Socket socket = serverSocket.accept();
                handleRequest(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket socket){
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hostName: " + socket.getInetAddress().getHostName()
        + " hostAddress: " + socket.getInetAddress().getHostAddress());
    }

}
