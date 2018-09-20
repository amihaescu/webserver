package ro.amihaescu.webserver.handlers;

import ro.amihaescu.webserver.Server;
import ro.amihaescu.webserver.dto.HttpRequest;
import ro.amihaescu.webserver.dto.HttpResponse;

public class DynamicHandler implements GenericHandler {
    @Override
    public HttpResponse handle(HttpRequest request, Server server) {
        return null;
    }
}
