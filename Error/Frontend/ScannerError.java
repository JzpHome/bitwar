package Error.Frontend;

import Error.BaseError;

@SuppressWarnings("serial")
public class ScannerError extends BaseError {
    public ScannerError(String message) {
        super("TokenScanner: " + message);
    }

    public ScannerError(String fpath, String message) {
        super("TokenScanner: " + fpath + ": " + message);
    }

    public ScannerError(String fpath, int lineno, int linepos, String message) {
        super("TokenScanner: " + fpath + ": " + lineno + ": " + linepos + " :" + message);
    }
}
