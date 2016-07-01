package rule.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Simple helper to convert an exception to a string.
 */
public class ExceptionHelper {
    private ExceptionHelper (){}

    public static final String toString(final Throwable inThrowable){
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter( stringWriter );
        inThrowable.printStackTrace( printWriter );
        printWriter.flush();
        return stringWriter.toString();
    }
}
