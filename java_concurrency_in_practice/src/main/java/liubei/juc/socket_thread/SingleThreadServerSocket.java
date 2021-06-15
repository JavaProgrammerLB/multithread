package liubei.juc.socket_thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SingleThreadServerSocket {

    private static final int NTHREADS = 100;
    private static final Executor executor = Executors.newFixedThreadPool(NTHREADS);

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
