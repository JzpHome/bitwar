package Error.Frontend;

import Error.BaseError;

@SuppressWarnings("serial")
public class ExprError extends BaseError {
    public ExprError(String type, String message) {
        super(type + message);
    }

    public ExprError(String type, String fpath, String message) {
        super(type + ": " + fpath + ": " + message);
    }

    public ExprError(String type, String fpath, int lineno, int linepos, String message) {
        super(type + ": " + fpath + ": " + lineno + ": " + linepos + " :" + message);
    }
}
