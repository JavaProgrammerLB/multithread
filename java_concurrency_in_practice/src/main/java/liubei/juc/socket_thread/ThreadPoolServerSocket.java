package liubei.juc.socket_thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadPoolServerSocket {

    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            final Socket socket = serverSocket.accept();
            Runnable task = () -> handleRequest(socket);
            exec.execute(task);
        }
    }

    private static void handleRequest(Socket socket) {
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread name:" + Thread.currentThread().getName() + "" +
                " socket address: " + socket.getInetAddress().getHostAddress());
    }
}
