package ro.amihaescu.webserver;

import org.springframework.http.HttpRequest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private Integer port;
    private ExecutorService executorService;

    Server(Integer port, Integer maxThreads) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }

    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Unable to start server");
            e.printStackTrace();
            return;
        }

        while (!Thread.interrupted()) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            executorService.execute(new Connection(clientSocket));
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Unable to stop server");
            e.printStackTrace();
        }
    }


}
