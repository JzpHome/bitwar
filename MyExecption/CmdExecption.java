package MyExecption;

@SuppressWarnings("serial")
public class CmdExecption extends Exception {
    private static final String tips = "you can input 'help' to see how to use";

    public CmdExecption(String message){
        super("命令错误: " + message + '\n' + tips);
    }

    public CmdExecption(String fpath, String message) {
        super("解析错误: " + fpath + ": " + message);
    }

    public CmdExecption(String fpath, int lineno, int linepos, String message) {
        super("解析错误: " + fpath + ": " + lineno + ": " + linepos + " :" + message);
    }
}
