package Compiler;

import java.io.*;

import Error.ResolveError;

@SuppressWarnings("all")
/**
 * 
 */
public class TokenScanner {
	/*
	 * file end && DFA State
	 */
	private static final int FILE_EOF			= -1;
	private static final int FLAG_START			= 0;
	private static final int FLAG_INCOMMENT		= 1;
	private static final int FLAG_INNUM			= 2;
	private static final int FLAG_INID			= 3;
	private static final int FLAG_INASSIGN		= 4;
	private static final int FLAG_DONE			= 100;
	private static final int FLAG_ERROR			= 101;

	/*
	 * Scanner基本属性
	 */
	private BufferedReader	file;
	private String			lineBuf;
	private int				lineNo;
	private int				lineIndex;
	private boolean			EOF_FLAG;
	/*
	 *
	 */
	private boolean			MATCH_FLAG;
    private String			currentToken;
	private SymbolTable		symbol;

	public TokenScanner(String fpath) {
		try {
			File				filer	= new File(fpath);
			FileInputStream		stream	= new FileInputStream(filer);
			InputStreamReader	in		= new InputStreamReader(stream);

			file = new BufferedReader(in);
		} catch (Exception e) {
			file = null;
			System.err.println(e);
		}

		lineBuf		 = "";
		lineNo		 = 1;
		lineIndex	 = 0;
		MATCH_FLAG	 = true;
		symbol		 = new SymbolTable();
	}

    public String getToken() throws ResolveError {
		int code  = -1;
		int	state = FLAG_START;

		// 利用DFA识别token
		if(MATCH_FLAG == true) {
			currentToken = "";
			while(state != FLAG_DONE) {
				code = getNextChar();
				switch(state) {
					case FLAG_START:
						state = START(code);
						break;
					case FLAG_INCOMMENT:
						state = INCOMMENT(code);
						break;
					case FLAG_INNUM:
						state = INNUM(code);
						break;
					case FLAG_INID:
						state = INID(code);
						break;
					case FLAG_INASSIGN:
						state = INASSIGN(code);
						break;
					case FLAG_ERROR:
						String error = "line:" + String.valueOf(lineNo) + ":" + lineBuf;
						throw (new ResolveError(error + ": syntax error"));
					default:
						throw (new ResolveError(state + "is not a state"));
				}
			}
			MATCH_FLAG = false;
		}

		if(currentToken == null || currentToken.equals("")) {
			return "";
		} else {
			return currentToken;
		}
    }

	public SymbolTable getSymbolTable() {
		return symbol;
	}

	public boolean match(String token) {
		if(currentToken.equals(token)) {
			MATCH_FLAG = true;
		} else {
			MATCH_FLAG = false;
		}
		return MATCH_FLAG;
	}

	public void close() {
		try {
			file.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private int getNextChar() {
		if(EOF_FLAG == true) {
			return FILE_EOF;
		} else if(!(lineIndex < lineBuf.length())) {
			lineNo++;
			try {
				lineBuf = file.readLine();
				if(lineBuf != null) {
					lineBuf += " ";
					lineIndex = 0;
				} else {
					EOF_FLAG = true;
					return FILE_EOF;
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		return lineBuf.charAt(lineIndex++);
	}
	
	private void ungetNextChar() {
		if(!EOF_FLAG) {
			lineIndex--;
		}
	}

	private int START(int code) {
		if(Character.isWhitespace(code)) {
			return FLAG_START;
		} else if(code == '{') {
			return FLAG_INCOMMENT;
		} else if(code == FILE_EOF) {
			return FLAG_DONE;
		} else {
			currentToken += (char)code;
			if(Character.isDigit(code)) {
				return FLAG_INNUM;
			} else if(Character.isLetter(code)) {
				return FLAG_INID;
			} else if(code == ':') {
				return FLAG_INASSIGN;
			} else {
				return FLAG_DONE;
			}
		}
	}

	private int INCOMMENT(int code) {
		if(code == FILE_EOF) {
			return FLAG_DONE;
		} else if(code == '}') {
			return FLAG_START;
		} else {
			return FLAG_INCOMMENT;
		}
	}

	private int INNUM(int code) {
		if(Character.isDigit(code)) {
			currentToken += (char)code;
			return FLAG_INNUM;
		} else if(Character.isLetter(code) || code == '_') {
			currentToken += (char)code;
			return FLAG_ERROR;
		} else {
			ungetNextChar();
			return FLAG_DONE;
		}
	}

	private int INID(int code) {
		if(Character.isLetter(code) || Character.isDigit(code) || code == '_') {
			currentToken += (char)code;
			return FLAG_INID;
		} else {
			if((!Keywords.match(currentToken)) && (!symbol.hasValue(currentToken))) {
				symbol.addValue(currentToken, 1);
			}
			ungetNextChar();
			return FLAG_DONE;
		}
	}

	private int INASSIGN(int code) {
		if(code == '=') {
			currentToken += (char)code;
			return FLAG_DONE;
		} else {
			ungetNextChar();
			return FLAG_ERROR;
		}
	}

    public static void main(String args[]) {
        try {
            TokenScanner scanner = new TokenScanner("Test/Resolve/TokenScanner.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				System.out.println("token: " + token);
				scanner.match(token);
				token = scanner.getToken();
			}
			scanner.close();
        } catch (ResolveError ce) {
            System.err.println(ce.getMessage());
        }
    }
}
