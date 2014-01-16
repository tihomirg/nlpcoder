package symbol;

import config.Config;

public class Terminals {

	public static final String S_DOT   = ".";
	public static final String S_L_PAR = "(";
	public static final String S_R_PAR = ")";
	public static final String S_COMMA = ",";
	public static final String S_SEMICOLON = ";";
	public static final String S_COLON = ":";	
	public static final String S_L_CURLY_BRACKET = "{";
	public static final String S_R_CURLY_BRACKET = "}";
	public static final String S_QUESTION_MARK = "?";
	public static final String S_L_SQUARE_BRACKET = "[";
	public static final String S_R_SQUARE_BRACKET = "]";
	public static final String S_L_TARG = "<";
	public static final String S_R_TARG = ">";
	public static final String S_EQ = "==";
	public static final String S_ASSIGN = "=";
	public static final String S_VARGS = "...";
	public static final String S_AND = "&";
	
	//------------------------------ Keywords ----------------------------------//
	public static final String S_NEW = "new";
	public static final String S_FOR = "for";	
	public static final String S_IF = "if";
	public static final String S_ELSE = "else";
	public static final String S_WHILE = "while";
	public static final String S_CASE = "case";
	public static final String S_SWITCH = "switch";
	public static final String S_THIS = "this";
	public static final String S_NULL = "null";
	public static final String S_SUPER = "super";
	public static final String S_BREAK = "break";
	public static final String S_CATCH = "catch";
	public static final String S_CONTINUE = "continue";
	public static final String S_DO = "do";
	public static final String S_INSTANCEOF = "instanceof";
	public static final String S_RETURN = "return";
	public static final String S_DEFAULT = "default";
	public static final String S_SYNCHRONIZED = "synchronized";
	public static final String S_THROWS = "throws";
	public static final String S_TRY = "try";
	public static final String S_FINALLY = "finally";
	public static final String S_INTERFACE = "interface";
	public static final String S_CLASS = "class";
	public static final String S_EXTENDS = "extends";
	public static final String S_IMPLEMENTS = "implements";
	public static final String S_VOID = "void";
	
	public static final Symbol DOT   = Config.getFactory().getTerminal(S_DOT);
	public static final Symbol L_PAR = Config.getFactory().getTerminal(S_L_PAR);
	public static final Symbol R_PAR = Config.getFactory().getTerminal(S_R_PAR);
	public static final Symbol COMMA = Config.getFactory().getTerminal(S_COMMA);
	public static final Symbol SEMICOLON = Config.getFactory().getTerminal(S_SEMICOLON);
	public static final Symbol COLON = 	Config.getFactory().getTerminal(S_COLON);
	public static final Symbol L_CURLY_BRACKET = Config.getFactory().getTerminal(S_L_CURLY_BRACKET);
	public static final Symbol R_CURLY_BRACKET = Config.getFactory().getTerminal(S_R_CURLY_BRACKET);
	public static final Symbol QUESTION_MARK = Config.getFactory().getTerminal(S_QUESTION_MARK);
	public static final Symbol L_SQUARE_BRACKET = Config.getFactory().getTerminal(S_L_SQUARE_BRACKET);
	public static final Symbol R_SQUARE_BRACKET = Config.getFactory().getTerminal(S_R_SQUARE_BRACKET);
	public static final Symbol L_TARG = Config.getFactory().getTerminal(S_L_TARG);
	public static final Symbol R_TARG = Config.getFactory().getTerminal(S_R_TARG);
	public static final Symbol EQ = Config.getFactory().getTerminal(S_EQ);
	public static final Symbol ASSIGN = Config.getFactory().getTerminal(S_ASSIGN);
	public static final Symbol VARGS = Config.getFactory().getTerminal(S_VARGS);
	public static final Symbol AND = Config.getFactory().getTerminal(S_AND);
	
	//------------------------------ Keywords ----------------------------------//
	public static final Symbol NEW = Config.getFactory().getTerminal(S_NEW);
	public static final Symbol FOR = Config.getFactory().getTerminal(S_FOR);
	public static final Symbol IF = Config.getFactory().getTerminal(S_IF);
	public static final Symbol ELSE = Config.getFactory().getTerminal(S_ELSE);
	public static final Symbol WHILE = Config.getFactory().getTerminal(S_WHILE);
	public static final Symbol CASE = Config.getFactory().getTerminal(S_CASE);
	public static final Symbol SWITCH = Config.getFactory().getTerminal(S_SWITCH);
	public static final Symbol THIS = Config.getFactory().getTerminal(S_THIS);
	public static final Symbol NULL = Config.getFactory().getTerminal(S_NULL);
	public static final Symbol SUPER = Config.getFactory().getTerminal(S_SUPER);
	public static final Symbol BREAK = Config.getFactory().getTerminal(S_BREAK);
	public static final Symbol CATCH = Config.getFactory().getTerminal(S_CATCH);
	public static final Symbol CONTINUE = Config.getFactory().getTerminal(S_CONTINUE);
	public static final Symbol DO = Config.getFactory().getTerminal(S_DO);
	public static final Symbol INSTANCEOF = Config.getFactory().getTerminal(S_INSTANCEOF);
	public static final Symbol RETURN = Config.getFactory().getTerminal(S_RETURN);
	public static final Symbol DEFAULT = Config.getFactory().getTerminal(S_DEFAULT);
	public static final Symbol SYNCHRONIZED = Config.getFactory().getTerminal(S_SYNCHRONIZED);
	public static final Symbol THROWS = Config.getFactory().getTerminal(S_THROWS);
	public static final Symbol TRY = Config.getFactory().getTerminal(S_TRY);
	public static final Symbol FINALLY = Config.getFactory().getTerminal(S_FINALLY);
	public static final Symbol INTERFACE = Config.getFactory().getTerminal(S_INTERFACE);
	public static final Symbol CLASS = Config.getFactory().getTerminal(S_CLASS);
	public static final Symbol EXTENDS = Config.getFactory().getTerminal(S_EXTENDS);
	public static final Symbol IMPLEMENTS = Config.getFactory().getTerminal(S_IMPLEMENTS);
	public static final Symbol VOID = Config.getFactory().getTerminal(S_VOID);

}
