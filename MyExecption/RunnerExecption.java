package MyExecption;

@SuppressWarnings("serial")
public class RunnerExecption extends Exception{
    public RunnerExecption(String message) {
        super("运行错误: " + message);
    }

    public RunnerExecption(String fpath, String message) {
        super("解析错误: " + fpath + ": " + message);
    }

    public RunnerExecption(String fpath, int lineno, int linepos, String message) {
        super("解析错误: " + fpath + ": " + lineno + ": " + linepos + " :" + message);
    }
}
