package Error;

@SuppressWarnings("serial")
public class ResolveError extends Exception{
    public ResolveError(String message) {
        super("解析错误: " + message);
    }
}
