package ro.amihaescu.webserver;

import ro.amihaescu.webserver.constans.ContentType;
import ro.amihaescu.webserver.constans.StatusCode;

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

    public HttpResponse withHtmlBody(String msg) {
        setContentLength(msg.getBytes().length);
        setContentType(ContentType.HTML);
        body = msg.getBytes();
        return this;
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
