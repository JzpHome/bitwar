import Error.BaseError;
import Frontend.Parser;
import Frontend.Statement.Stategy;

public class AST {
    public static void main(String args[]) {
		try {
			Stategy stategy = null;
			for(int i = 0; i < args.length; ++i) {
				stategy = Parser.parse(args[i]);

				System.out.println(stategy.toString());
				stategy.print(0);
			}
		} catch (BaseError e) {
			System.out.println(e.getMessage());
		}
    }

}
