package ro.amihaescu.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private Integer port;
    private ExecutorService executorService;
    private String webRoot;
    private Long connectionKeepAliveTime;

    Server(Integer port, Integer maxThreads, String webRoot, Long connectionKeepAliveTime) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(maxThreads);
        this.webRoot = webRoot;
        this.connectionKeepAliveTime = connectionKeepAliveTime;
    }

    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.printf("%s - Unable to start server\n", new Date());
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
            System.out.printf("%s - %s - Accepting connection \n" ,Thread.currentThread(), new Date());
            executorService.execute(new Connection(clientSocket, this,
                    System.currentTimeMillis() + connectionKeepAliveTime));
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.printf("%s - Unable to stop server", new Date());
            e.printStackTrace();
        }
    }

    public String getWebRoot() {
        return webRoot;
    }
}
