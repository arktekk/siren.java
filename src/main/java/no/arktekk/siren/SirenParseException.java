package no.arktekk.siren;

public class SirenParseException extends RuntimeException {
    public SirenParseException() {
        super();
    }

    public SirenParseException(String message) {
        super(message);
    }

    public SirenParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SirenParseException(Throwable cause) {
        super(cause);
    }
}
