package se.chalmers.student.devit.resekompanjon.backend.connectionBackend;

/**
 * Created by Marcus on 2015-10-15.
 */
public class NoJsonAvailableException extends Exception {

    public NoJsonAvailableException() {
        super();
    }

    public NoJsonAvailableException(String message) {
        super(message);
    }
}
