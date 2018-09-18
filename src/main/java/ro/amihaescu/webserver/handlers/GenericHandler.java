package ro.amihaescu.webserver.handlers;

import ro.amihaescu.webserver.dto.HttpRequest;
import ro.amihaescu.webserver.Server;
import ro.amihaescu.webserver.dto.HttpResponse;

@FunctionalInterface
public interface GenericHandler {

    HttpResponse handle(HttpRequest request, Server server);
}
