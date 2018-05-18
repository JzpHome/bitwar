package Error.Optimizer;

import Error.BaseError;

@SuppressWarnings("serial")
public class TransError extends BaseError {
    public TransError(String message){
        super("Backend: " + message );
    }
}
