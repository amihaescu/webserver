package ro.amihaescu.webserver.constans;

public enum HttpMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    static HttpMethod parseString(String method){
        if ("get".equalsIgnoreCase(method)){
            return GET;
        } else {
            return POST;
        }
    }
}
