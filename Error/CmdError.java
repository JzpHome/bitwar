package Error;

@SuppressWarnings("serial")
public class CmdError extends Exception {
    private static final String tips = "you can input 'help' to see how to use";

    public CmdError(String message){
        super("命令错误: " + message + '\n' + tips);
    }
}
