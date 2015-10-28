package se.chalmers.student.devit.resekompanjon.backend.connectionBackend;

/**
 * Created by Marcus on 2015-10-20.
 */
public class NoTripFoundException extends Exception {

    public NoTripFoundException() {
        super();
    }

    public NoTripFoundException(String message) {
        super(message);
    }
}
