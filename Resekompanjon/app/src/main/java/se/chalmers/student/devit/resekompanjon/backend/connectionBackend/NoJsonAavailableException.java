package se.chalmers.student.devit.resekompanjon.backend.connectionBackend;

/**
 * Created by Marcus on 2015-10-15.
 */
public class NoJsonAavailableException extends Exception {

    public NoJsonAavailableException (){
        super();
    }

    public NoJsonAavailableException(String message) {
        super(message);
    }
}
