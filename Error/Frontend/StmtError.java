package Error.Frontend;

import Error.BaseError;

@SuppressWarnings("serial")
public class StmtError extends BaseError {
    public StmtError(String type, String message) {
        super(type + ": " + message);
    }

    public StmtError(String type, String fpath, String message) {
        super(type + ": " + fpath + ": " + message);
    }

    public StmtError(String type, String fpath, int lineno, int linepos, String message) {
        super(type + ": " + fpath + ": " + lineno + ": " + linepos + " :" + message);
    }
}
