package ro.amihaescu.webserver;

public class Main {

    public static void main(String[] args) {
        new Thread(new Server(8080, 10, "www")).start();
        System.out.println("==== Started server ====");
    }
}
