package Frontend;

import java.io.File;
import Error.BaseError;
import Frontend.Statement.Stategy;

@SuppressWarnings("all")
public class Parser {
    public static Stategy parse(String fpath) throws BaseError {
		TokenScanner scanner = new TokenScanner(fpath);

		String token = new String(scanner.getToken());
		if(token.equals("Stategy")) {
			Stategy stategy = new Stategy(scanner);
			return stategy;
		} else {
			throw (new BaseError("Parser: " + token + " is not 'Stategy'"));
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

		} catch (BaseError e) {
			System.out.println(e.getMessage());
		}
    }
}
