package Frontend;

// 前端
import java.io.File;
import Frontend.Statement.Stategy;
// 错误异常
import MyExecption.*;

@SuppressWarnings("all")
public class Parser {
    public static Stategy parse(String fpath) throws FrontendExecption {
		TokenScanner scanner = new TokenScanner(fpath);

		String token = new String(scanner.getToken());
		if(token.equals("Stategy")) {
			Stategy stategy = new Stategy(scanner);
			return stategy;
		} else {
			throw (new FrontendExecption("Parser: " + token + " is not 'Stategy'"));
		}
    }

    public static void main(String args[]) {
		try {
			Stategy	stategy = parse("Test/Frontend/Parser_01.txt");
			System.out.println(stategy.toString());

			stategy = parse("Test/Frontend/Parser_02.txt");
			System.out.println(stategy.toString());

			stategy = parse("Test/Frontend/Parser_03.txt");
			System.out.println(stategy.toString());

		} catch (FrontendExecption re) {
			System.out.println(re.getMessage());
		}
    }
}
