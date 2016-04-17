/**
 * Software: Top Sales
 *
 * Module: JSON parsing failed exception class
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


public class JsonParseFailedException extends Exception {

    private static final long serialVersionUID = 1997753363232807009L;

    public JsonParseFailedException() {
    }

    public JsonParseFailedException(String message) {
        super(message);
    }

    public JsonParseFailedException(Throwable cause) {
        super(cause);
    }

    public JsonParseFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonParseFailedException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
