package ro.amihaescu.webserver.handlers;


import ro.amihaescu.webserver.dto.HttpRequest;
import ro.amihaescu.webserver.Server;
import ro.amihaescu.webserver.constants.StatusCode;
import ro.amihaescu.webserver.dto.HttpResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.stream.Collectors;

public class StaticHandler implements GenericHandler {

    @Override
    public HttpResponse handle(HttpRequest request, Server server) {
        HttpResponse httpResponse;
        String uri = request.getUrl();
        File f = new File(String.format("%s%s", server.getWebRoot(), uri));

        System.out.printf("%s - %s - Handling request for %s \n", Thread.currentThread(), new Date(), uri);
        try {
            httpResponse = new HttpResponse(StatusCode.OK).withFile(f);
        } catch (FileNotFoundException e) {
            File defaultPage = new File(getClass().getClassLoader().getResource("default.html").getFile());
            httpResponse = new HttpResponse(StatusCode.NOT_FOUND)
                    .withHtmlBody(replaceFieldName(defaultPage, uri));
        }
        return httpResponse;
    }

    private String replaceFieldName(File file, String fileName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            return bufferedReader.lines()
                    .map(line -> line.replaceAll("#file#", fileName))
                    .collect(Collectors.joining());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
