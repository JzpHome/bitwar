package Error.Backend;

import Error.BaseError;

@SuppressWarnings("serial")
public class TransError extends BaseError {
    public TransError(String type, String message){
        super(type + ": " + message );
    }
}
