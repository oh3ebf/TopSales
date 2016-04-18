/**
 * Software: Top Sales
 *
 * Module: HTTP request failed exception class
 *
 * Version: 0.1
 *
 * Licence: GPL2
 *
 * Owner: Kim Kristo
 *
 * Date creation : 15.4.2016
 *
 */
package oh3ebf.topsales.exceptions;


public class RequestFailedException extends Exception {

    private static final long serialVersionUID = 1997753363232807009L;

    public RequestFailedException() {
    }

    public RequestFailedException(String message) {
        super(message);
    }

    public RequestFailedException(Throwable cause) {
        super(cause);
    }

    public RequestFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestFailedException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
