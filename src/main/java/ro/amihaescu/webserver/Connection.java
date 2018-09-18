package ro.amihaescu.webserver;


import ro.amihaescu.webserver.constans.HttpMethod;
import ro.amihaescu.webserver.dto.HttpResponse;
import ro.amihaescu.webserver.dto.HttpRequest;
import ro.amihaescu.webserver.handlers.GenericHandler;
import ro.amihaescu.webserver.handlers.GetHandler;

import java.io.*;
import java.net.Socket;
import java.util.*;

import static ro.amihaescu.webserver.constans.HttpMethod.GET;

public class Connection implements Runnable {

    private Socket socket;
    private Server server;
    private Long connectionKeepAliveTime;
    private Boolean connectionKeepAlive = false;
    private static Map<HttpMethod, GenericHandler> handlers;
    private Timer timer;

    static {
        handlers = new HashMap<>();
        handlers.put(GET, new GetHandler());
    }

    public Connection(Socket socket, Server server, Long connectionKeepAliveTime) {
        this.socket = socket;
        this.server = server;
        this.connectionKeepAliveTime = connectionKeepAliveTime;
        this.timer = new Timer();
    }

    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            do {
                HttpRequest httpRequest = HttpRequest.parseHttpRequest(inputStream);
                Map<String, String> headers = httpRequest.getHeaders();

                System.out.printf("%s - %s - Handling request for %s \n", Thread.currentThread().getName(), new Date(), httpRequest.getUrl());

                GenericHandler genericHandler = handlers.get(httpRequest.getMethod());
                HttpResponse httpResponse = genericHandler.handle(httpRequest, server);
                setConnectionKeepAlive(headers, httpResponse);

                PrintWriter printWriter = new PrintWriter(outputStream);
                printWriter.write(httpResponse.toString());
                printWriter.flush();
            } while (connectionKeepAlive && System.currentTimeMillis() < connectionKeepAliveTime);
            System.out.printf("%s - %s - closed connection \n", Thread.currentThread(), new Date());
            connectionKeepAlive = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setConnectionKeepAlive(Map<String, String> headers, HttpResponse httpResponse) {
        if (headers.containsKey("Connection") &&
                "keep-alive".equalsIgnoreCase(headers.get("Connection"))) {
            connectionKeepAlive = true;
            httpResponse.setConnectionKeepAlive(true);
        } else {
            httpResponse.setConnectionKeepAlive(false);
            connectionKeepAlive = false;
        }
    }


}
