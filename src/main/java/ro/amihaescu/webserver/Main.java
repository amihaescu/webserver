package ro.amihaescu.webserver;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Thread thread = new Thread(new Server(8080, 2, "www"));
        //thread.setDaemon(true);
        thread.start();
        System.out.println(String.format("%s - %s - Started server", Thread.currentThread(), new Date()));
    }
}
