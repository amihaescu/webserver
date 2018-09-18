package ro.amihaescu.webserver;

import java.util.Date;

public class Main {

    private static final Integer DEFAULT_PORT = 8080;
    private static final Integer DEFAULT_NO_THREADS = 3;
    private static final String DEFAULT_WEB_ROOT = "www";

    private static Integer serverPort;
    private static Integer noThreads;
    private static String webRoot;

    public static void main(String[] args) {


        try {
            if (args.length == 3) {
                serverPort = Integer.valueOf(args[0]);
                noThreads = Integer.valueOf(args[1]);
                webRoot = args[2];
            } else {
                defaultInit();
            }
        } catch (NumberFormatException e) {

            defaultInit();
        }

        Thread thread = new Thread(new Server(8080, 3, "www", 10_000L));
        thread.start();
        System.out.printf("%s - Server will run on port %s; \n" , new Date(), serverPort);
        System.out.printf("%s - Number of threads  %s; \n" , new Date(), noThreads);
        System.out.printf("%s - The web root for the server is %s; \n" , new Date(), webRoot);

        System.out.printf("%s - Started server \n", new Date());


    }

    private static void defaultInit() {
        System.out.printf("%s - Using default values \n", new Date());
        serverPort = DEFAULT_PORT;
        noThreads = DEFAULT_NO_THREADS;
        webRoot = DEFAULT_WEB_ROOT;
    }
}
