package ro.amihaescu.webserver.dto;

import ro.amihaescu.webserver.constants.ContentType;
import ro.amihaescu.webserver.constants.StatusCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {

    private static final String protocol = "HTTP/1.0";

    private StatusCode status;
    private Map<String, Object> headers = new LinkedHashMap<>();
    private byte[] body = null;

    public HttpResponse(StatusCode status) {
        this.status = status;
    }

    public HttpResponse withFile(File file) throws FileNotFoundException {
        if (file.isFile()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                int length = fileInputStream.available();
                body = new byte[length];
                fileInputStream.read(body);
                fileInputStream.close();

                setContentLength(length);
                if (file.getName().endsWith(".htm") || file.getName().endsWith(".html")) {
                    setContentType(ContentType.HTML);
                } else if (file.getName().endsWith(".js")) {
                    setContentType(ContentType.JS);
                } else if (file.getName().endsWith(".css")) {
                    setContentType(ContentType.CSS);
                }
            } catch (FileNotFoundException e) {
                throw e;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        } else {
            throw new FileNotFoundException(file.getName());
        }
    }

    public HttpResponse withHtmlBody(String msg) {
        setContentLength(msg.getBytes().length);
        setContentType(ContentType.HTML);
        body = msg.getBytes();
        return this;
    }

    public void setConnectionKeepAlive(boolean b){
        if (b) {
            headers.put("Connection", "keep-Alive");
        } else {
            headers.put("Connection", "close");
        }
    }

    public void setDate(Date date) {
        headers.put("Date", date.toString());
    }

    public void setContentLength(long value) {
        headers.put("Content-Length", String.valueOf(value));
    }

    public void setContentType(ContentType value) {
        headers.put("Content-Type", value);
    }

    public void removeBody(){
        body = null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(protocol + " " + status + "\n");
        for (String key : headers.keySet()) {
            result.append(key).append(": ").append(headers.get(key)).append("\n");
        }
        result.append("\r\n");
        if (body != null) {
            result.append(new String(body));
        }
        return result.toString();
    }

}
