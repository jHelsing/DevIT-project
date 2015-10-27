package se.chalmers.student.devit.resekompanjon.backend.connectionBackend;

/**
 * Created by Marcus on 2015-10-27.
 */

public class NoTripsForDateException extends Exception{

    public NoTripsForDateException() {
        super();
    }

    public NoTripsForDateException(String message) {
        super(message);
    }
}