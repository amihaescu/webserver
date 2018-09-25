package ro.amihaescu.webserver.handlers;

import org.codehaus.jackson.map.ObjectMapper;
import ro.amihaescu.webserver.Server;
import ro.amihaescu.webserver.constants.ContentType;
import ro.amihaescu.webserver.constants.StatusCode;
import ro.amihaescu.webserver.dto.HttpRequest;
import ro.amihaescu.webserver.dto.HttpResponse;
import ro.amihaescu.webserver.web.MethodPath;
import ro.amihaescu.webserver.web.ObjectMethod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class DynamicHandler implements GenericHandler {

    private ObjectMapper objectMapper;

    public DynamicHandler() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public HttpResponse handle(HttpRequest request, Server server) {
        MethodPath methodPath = new MethodPath(request.getMethod(), request.getUrl());
        Map<MethodPath, ObjectMethod> mappings = server.getMappings();
        ObjectMethod objectMethod = mappings.get(methodPath);
        HttpResponse httpResponse = null;
        try {
            Method method = objectMethod.getMethod();
            Object object = method.invoke(objectMethod.getObject(), null);
            httpResponse = new HttpResponse(StatusCode.OK)
                    .withJsonResponse(objectMapper.writeValueAsString(object));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }

        return httpResponse;
    }
}
