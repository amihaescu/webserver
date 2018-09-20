package ro.amihaescu.webserver.constants;

public enum ContentType {
    TEXT("text/plain"),
    HTML("text/html"),
    JS("application/js"),
    CSS("text/css");
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
