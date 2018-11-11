package ro.amihaescu.webserver;

import lombok.Getter;
import org.reflections.Reflections;
import ro.amihaescu.webserver.handlers.DynamicHandler;
import ro.amihaescu.webserver.handlers.GenericHandler;
import ro.amihaescu.webserver.handlers.StaticHandler;
import ro.amihaescu.webserver.web.Connection;
import ro.amihaescu.webserver.annotations.EndPoint;
import ro.amihaescu.webserver.annotations.RestController;
import ro.amihaescu.webserver.constants.HttpMethod;
import ro.amihaescu.webserver.web.MethodPath;
import ro.amihaescu.webserver.web.ObjectMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Server implements Runnable {

    private Integer port;
    private ExecutorService executorService;
    private String webRoot;
    @Getter
    private Map<MethodPath, ObjectMethod> mappings;
    @Getter
    private Map<String, GenericHandler> handlers;

    Server(Integer port, Integer maxThreads, String webRoot) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(maxThreads);
        this.webRoot = webRoot;
        this.mappings = new HashMap<>();
        initContext();
        initHandlers();
    }

    private void initHandlers() {
        handlers = new HashMap<>();
        handlers.put("static", new StaticHandler());
        handlers.put("dyanmic", new DynamicHandler());
    }

    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Unable to start server");
            e.printStackTrace();
            return;
        }

        while (!Thread.interrupted()) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            executorService.execute(new Connection(clientSocket, this));
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Unable to stop server");
            e.printStackTrace();
        }
    }

    public String getWebRoot() {
        return webRoot;
    }


    private void initContext() {
        Reflections reflections = new Reflections("ro.amihaescu.webserver.endpoints");
        Set<Class<?>> modules = reflections.getTypesAnnotatedWith(RestController.class);

        for (Class<?> classItt : modules) {
            List<Method> methodList = Arrays.stream(classItt.getMethods())
                    .filter(method -> method.isAnnotationPresent(EndPoint.class))
                    .collect(Collectors.toList());
            Object c = null;
            try {
                 c = classItt.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            for (Method method : methodList) {
                EndPoint endPoint = method.getAnnotation(EndPoint.class);
                HttpMethod httpMethod = endPoint.method();
                String path = endPoint.path();
                mappings.put(new MethodPath(httpMethod, path), new ObjectMethod(c, method));
            }
        }
    }


}
