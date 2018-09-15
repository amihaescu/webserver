package ro.amihaescu.webserver;

import ro.amihaescu.webserver.constans.HttpMethod;
import ro.amihaescu.webserver.constans.StatusCode;
import ro.amihaescu.webserver.dto.HttpRequest;
import ro.amihaescu.webserver.dto.HttpResponse;
import ro.amihaescu.webserver.handlers.GenericHandler;
import ro.amihaescu.webserver.handlers.GetHandler;

import java.io.*;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static ro.amihaescu.webserver.constans.HttpMethod.GET;
import static ro.amihaescu.webserver.constans.StatusCode.OK;

public class Connection implements Runnable {

    private Socket socket;
    private Server server;
    private static Map<HttpMethod, GenericHandler> handlers;

    static {
        handlers = new HashMap<>();
        handlers.put(GET, new GetHandler());
    }

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            Long currentTime = System.currentTimeMillis();
            HttpResponse httpResponse = new HttpResponse(OK);
            while (System.currentTimeMillis() < currentTime + 10_000) {
                HttpRequest httpRequest = HttpRequest.parseHttpRequest(inputStream);
                Map<String, String> headers = httpRequest.getHeaders();

                System.out.println(String.format("%s - %s - Handling request for %s", Thread.currentThread().getName(), new Date(), httpRequest.getUrl()));

                GenericHandler genericHandler = handlers.get(httpRequest.getMethod());
                httpResponse = genericHandler.handle(httpRequest, server);

                if (headers.containsKey("Connection")) {
                    if ("keep-alive".equalsIgnoreCase(headers.get("Connection"))) {
                        httpResponse.setConnectionKeepAlive(true);
                        PrintWriter printWriter = new PrintWriter(outputStream);
                        printWriter.write(httpResponse.toString());
                        printWriter.flush();
                    }
                    else
                    {
                        httpResponse.setConnectionKeepAlive(false);
                        PrintWriter printWriter = new PrintWriter(outputStream);
                        printWriter.write(httpResponse.toString());
                        printWriter.flush();
                        break;
                    }
                }
            }
            httpResponse.setConnectionKeepAlive(false);
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(httpResponse.toString());
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
