package ro.amihaescu.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {

    private Socket socket = null;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream())
        {
            PrintWriter printWriter = new PrintWriter(outputStream);
            String responseHeader =
                    "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/html; charset=UTF-8\r\n" +
                            "Content-Length: " + "Hello!".length() +
                            "\r\n\r\n";
            printWriter.write(responseHeader);
            printWriter.write("Hello!");
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
