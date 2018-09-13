package ro.amihaescu.webserver.constans;

public enum ContentType {
    TEXT("text/plain"),
    HTML("text/html");

    private String text;

    ContentType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
