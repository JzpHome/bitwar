package Frontend;

import java.io.*;
import Error.Frontend.ScannerError;

@SuppressWarnings("all")
public class TokenScanner {
	/*
	 * __file end && DFA State
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
	private String			__fpath;
	private BufferedReader	__file;
	private String			__line;
	private int				__lineno;
	private int				__linepos;
	private boolean			EOF_FLAG;
	/*
	 *
	 */
	private boolean			MATCH_FLAG;
    private String			tokens;

	public TokenScanner(String fpath) {
		try {
			File				filer	= new File(fpath);
			FileInputStream		stream	= new FileInputStream(filer);
			InputStreamReader	in		= new InputStreamReader(stream);

			__fpath = new String(fpath);
			__file	= new BufferedReader(in);
		} catch (Exception e) {
			__file = null;
			System.err.println(e);
		}

		__line		= null;
		__lineno	= 0;
		__linepos	= 0;
		MATCH_FLAG	= true;
	}

	public String fpath() {
		return (new String(__fpath));
	}

	public int lineno() {
		return __lineno;
	}

	public int linepos() {
		return __linepos;
	}

    public String getToken() throws ScannerError {
		int code  = -1;
		int	state = FLAG_START;

		// 利用DFA识别token
		if(MATCH_FLAG == true) {
			tokens = null;
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
						ERROR();
						break;
					default:
						throw (new ScannerError(state + "is not a state"));
				}
			}
			MATCH_FLAG = false;
		}
		return DONE();
    }

	public boolean match(String token) {
		if(tokens.equals(token)) {
			MATCH_FLAG = true;
		} else {
			MATCH_FLAG = false;
		}
		return MATCH_FLAG;
	}

	public void close() {
		try {
			__file.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private int getNextChar() {
		if(EOF_FLAG == true) {
			return FILE_EOF;
		} else if((__line == null) || (!(__linepos < __line.length()))) {
			__lineno++;
			try {
				__line = __file.readLine();
				if(__line != null) {
					__line += " ";
					__linepos = 0;
				} else {
					EOF_FLAG = true;
					return FILE_EOF;
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		return __line.charAt(__linepos++);
	}
	
	private void ungetNextChar() {
		if(!EOF_FLAG) {
			__linepos--;
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
			tokens = "" + (char)code;
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
			tokens += (char)code;
			return FLAG_INNUM;
		} else if(Character.isLetter(code) || code == '_') {
			ungetNextChar();
			return FLAG_ERROR;
		} else {
			ungetNextChar();
			return FLAG_DONE;
		}
	}

	private int INID(int code) {
		if(Character.isLetter(code) || Character.isDigit(code) || code == '_') {
			tokens += (char)code;
			return FLAG_INID;
		} else {
			ungetNextChar();
			return FLAG_DONE;
		}
	}

	private int INASSIGN(int code) {
		if(code == '=') {
			tokens += (char)code;
			return FLAG_DONE;
		} else {
			ungetNextChar();
			return FLAG_DONE;
		}
	}

	private String DONE() {
		if(tokens == null) {
			return "";
		} else {
			return (new String(tokens));
		}
	}

	private void ERROR() throws ScannerError {
		ungetNextChar();
		throw (new ScannerError(__fpath, __lineno, __linepos, "syntax error"));
	}

    public static void main(String args[]) {
        try {
            TokenScanner scanner = new TokenScanner("Test/Frontend/TokenScanner.txt");

			String token = scanner.getToken();
			while(!token.equals("")) {
				System.out.println("Token: " + token);
				scanner.match(token);
				token = scanner.getToken();
			}
			scanner.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
