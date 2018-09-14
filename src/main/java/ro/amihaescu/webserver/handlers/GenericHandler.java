package ro.amihaescu.webserver.handlers;

import ro.amihaescu.webserver.HttpRequest;
import ro.amihaescu.webserver.HttpResponse;
import ro.amihaescu.webserver.Server;

@FunctionalInterface
public interface GenericHandler {

    HttpResponse handle(HttpRequest request, Server server);
}
