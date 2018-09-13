package ro.amihaescu.webserver;

import ro.amihaescu.webserver.constans.StatusCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {

    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream())
        {
            HttpRequest httpRequest = HttpRequest.parseHttpRequest(inputStream);

            PrintWriter printWriter = new PrintWriter(outputStream);
            HttpResponse httpResponse = new HttpResponse(StatusCode.OK).withHtmlBody("<p>Done</p>");
            printWriter.write(httpResponse.toString());
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
