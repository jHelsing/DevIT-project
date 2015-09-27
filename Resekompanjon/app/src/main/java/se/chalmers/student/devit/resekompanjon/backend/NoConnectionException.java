package se.chalmers.student.devit.resekompanjon.backend;

/**
 * 
 *
 * @author Jonathan
 * @version 1.0
 */
public class NoConnectionException extends Exception {

    public NoConnectionException() {
        super();
    }

    public NoConnectionException(String message) {
        super(message);
    }
}
