package ro.amihaescu.webserver.dto;

import ro.amihaescu.webserver.constants.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    private HttpMethod method;
    private String url;
    private String protocol;
    private Map<String, String> headers = new HashMap<>();
    private List<String> body = new ArrayList<>();

    private HttpRequest() {
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public List<String> getBody() {
        return body;
    }

    public static HttpRequest parseHttpRequest(InputStream in) {
        HttpRequest httpRequest = new HttpRequest();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        try {
            String line = bufferedReader.readLine();
            if (line == null) {
                throw new IOException("Empty line received");
            }
            String[] requestElements = line.split(" ");
            if (requestElements.length != 3) {
                throw new IOException(String.format("Cannot parse request from %s", line));
            }
            if (!requestElements[2].startsWith("HTTP/")) {
                throw  new IOException("Server only accepts HTTP requests");
            }
            httpRequest.method = HttpMethod.valueOf(requestElements[0]);
            httpRequest.url = requestElements[1];
            httpRequest.protocol = requestElements[2];

            // build header
            line = bufferedReader.readLine();
            while (line != null && !line.isEmpty()){
                String[] header = line.split(": ", 2);
                if (header.length != 2) {
                    throw new IOException(String.format("Cannot parse header from %s", line));
                } else {
                    httpRequest.headers.put(header[0], header[1]);
                }
                line = bufferedReader.readLine();
            }

            //build body
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                httpRequest.body.add(line);
            }
            return httpRequest;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }



    }
}
