package ro.amihaescu.webserver.constans;

public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "Not found"),
    NOT_IMPLEMENTED(501, "Not implemented");

    private int number;
    private String text;

    StatusCode(int number, String text) {
        this.number = number;
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
