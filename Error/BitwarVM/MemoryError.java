package Error.BitwarVM;

import Error.BaseError;

@SuppressWarnings("serial")
public class MemoryError extends BaseError {
    public MemoryError(String type, String message) {
        super(type + ": " + message);
    }
}
