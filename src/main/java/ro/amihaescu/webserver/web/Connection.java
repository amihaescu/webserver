package ro.amihaescu.webserver.web;


import ro.amihaescu.webserver.Server;
import ro.amihaescu.webserver.constants.HttpMethod;
import ro.amihaescu.webserver.dto.HttpResponse;
import ro.amihaescu.webserver.dto.HttpRequest;
import ro.amihaescu.webserver.handlers.DynamicHandler;
import ro.amihaescu.webserver.handlers.GenericHandler;
import ro.amihaescu.webserver.handlers.StaticHandler;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static ro.amihaescu.webserver.constants.HttpMethod.GET;

public class Connection implements Runnable {

    private Socket socket;
    private Server server;
    private static Map<String, GenericHandler> handlers;

    static {
        handlers = new HashMap<>();
        handlers.put("static", new StaticHandler());
        handlers.put("dyanmic", new DynamicHandler());
    }

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.parseHttpRequest(inputStream);

            GenericHandler genericHandler = handlers.get(httpRequest.getUrl().contains(".") ? "static" : "dyanmic");
            HttpResponse httpResponse = genericHandler.handle(httpRequest, server);

            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(httpResponse.toString());
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
