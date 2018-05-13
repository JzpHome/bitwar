package MyExecption;

@SuppressWarnings("serial")
public class FrontendExecption extends Exception{
    public FrontendExecption(String message) {
        super("解析错误: " + message);
    }

    public FrontendExecption(String fpath, String message) {
        super("解析错误: " + fpath + ": " + message);
    }

    public FrontendExecption(String fpath, int lineno, int linepos, String message) {
        super("解析错误: " + fpath + ": " + lineno + ": " + linepos + " :" + message);
    }
}
