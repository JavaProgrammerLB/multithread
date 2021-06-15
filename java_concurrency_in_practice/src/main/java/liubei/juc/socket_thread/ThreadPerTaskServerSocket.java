package liubei.juc.socket_thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerTaskServerSocket {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8080);
        while (true) {
            final Socket connection = socket.accept();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    handleRequest(connection);
                }
            };

            new Thread(runnable).start();
        }
    }

    private static void handleRequest(Socket socket) {
        System.out.println("thread Name: " + Thread.currentThread().getName()
                + socket.getInetAddress().getAddress());
    }

}
