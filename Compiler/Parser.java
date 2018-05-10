package Compiler;

import java.io.File;
// 
import Statement.Stategy;
// 错误异常
import Error.ResolveError;

@SuppressWarnings("all")
public class Parser {
	private Stategy		stategy;
	private SymbolTable symbol;

    public Parser(String fpath) throws ResolveError {
		TokenScanner scanner = new TokenScanner(fpath);

		stategy = new Stategy(scanner);
		symbol	= scanner.getSymbolTable();
    }

	public Stategy getStategy() {
		return stategy;
	}

	public SymbolTable getSymbolTable() {
		return symbol;
	}

    public static void main(String args[]) {
        try {
			Parser parser = new Parser("Test/Resolve/Parser.txt");

            Stategy		stategy = parser.getStategy();
			SymbolTable symbol	= parser.getSymbolTable();

			stategy.print();
			symbol.print();
        } catch (ResolveError ce){
            System.err.println(ce.getMessage());
        }
    }
}
