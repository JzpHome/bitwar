package Error.BitwarVM;

import Error.BaseError;

@SuppressWarnings("serial")
public class VMError extends BaseError {
    public VMError(String type, String message) {
        super(type + ": " + message);
    }
}
