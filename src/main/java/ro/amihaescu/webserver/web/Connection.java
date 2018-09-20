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

public class Connection implements Runnable {

    private Socket socket;
    private Server server;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.parseHttpRequest(inputStream);

            String key = httpRequest.getUrl().contains(".") ? "static" : "dyanmic";
            GenericHandler genericHandler = server.getHandlers().get(key);
            HttpResponse httpResponse = genericHandler.handle(httpRequest, server);

            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(httpResponse.toString());
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
