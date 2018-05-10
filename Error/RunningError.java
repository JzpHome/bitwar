package Error;

@SuppressWarnings("serial")
public class RunningError extends Exception{
    public RunningError(String message) {
        super("运行错误: " + message);
    }
}
