package Error.Frontend;

import Error.BaseError;

@SuppressWarnings("serial")
public class ParserError extends BaseError {
    public ParserError(String message) {
        super("ParserError: " + message);
    }

    public ParserError(String fpath, String message) {
        super("ParserError: " + fpath + ": " + message);
    }

    public ParserError(String fpath, int lineno, int linepos, String message) {
        super("ParserError: " + fpath + ": " + lineno + ": " + linepos + " :" + message);
    }
}
