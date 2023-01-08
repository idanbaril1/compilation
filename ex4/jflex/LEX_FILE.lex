/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column

/*******************************************************************************/
/* Note that this has to be the EXACT same name of the class the CUP generates */
/*******************************************************************************/
%cupsym TokenNames

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup

/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; } 

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/
	public int getTokenStartPosition() { return yycolumn + 1; } 
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator	= \r|\n|\r\n
WhiteSpaceWithoutLineTerminator		= [\t] | [ ]
WhiteSpace		= {LineTerminator} | {WhiteSpaceWithoutLineTerminator}
DIGIT           = [0-9]
INTEGER			= 0 | [1-9]{DIGIT}*
ID				= [a-z|A-Z][a-z|A-Z|0-9]*
LETTER          = [a-z|A-Z]
STRING			= \"{LETTER}*\"
BRACKET         = \( | \) | \[ | \] | \{ | \}
MARK            = \? | \!
OPERATORNOSTAR  = \+ | \- | \/
OPERATORNOSLASH = \+ | \- | \*
OPERATOR1       = {OPERATORNOSLASH} | \/
OPERATOR2       = {OPERATORNOSTAR} | \*({LETTER}|{DIGIT}|{WhiteSpace}|{LineTerminator}|{BRACKET}|{MARK}|{OPERATORNOSLASH}|{DOTS})
DOTS            = \. | \;
COMMENT1OPENER  = \/\/
COMMENT2OPENER  = \/\*
COMMENT2CLOSER  = \*\/
COMMENT1        = {COMMENT1OPENER}({LETTER}|{DIGIT}|{WhiteSpaceWithoutLineTerminator}|{BRACKET}|{MARK}|{OPERATOR1}|{DOTS})*{LineTerminator}
COMMENT2        = {COMMENT2OPENER}({LETTER}|{DIGIT}|{WhiteSpace}|{LineTerminator}|{BRACKET}|{MARK}|{OPERATOR2}|{DOTS})*[\*]?{COMMENT2CLOSER}
COMMENT         = ({COMMENT1}|{COMMENT2})
INVALIDCOMMENT  = ({COMMENT1OPENER}|{COMMENT2OPENER})
ERRORCATCHER    = .

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/

/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/

<YYINITIAL> {
"string"			{ return symbol(TokenNames.TYPE_STRING);}
"int"				{ return symbol(TokenNames.TYPE_INT);}	
"void"				{ return symbol(TokenNames.TYPE_VOID);}
"extends"			{ return symbol(TokenNames.EXTENDS);}
"array"				{ return symbol(TokenNames.ARRAY);}	
"while"				{ return symbol(TokenNames.WHILE);}
"new"				{ return symbol(TokenNames.NEW);}	
"return"			{ return symbol(TokenNames.RETURN);}
"if"				{ return symbol(TokenNames.IF);}
"class"				{ return symbol(TokenNames.CLASS);}
"\+"				{ return symbol(TokenNames.PLUS);}
"-"					{ return symbol(TokenNames.MINUS);}
"\*"				{ return symbol(TokenNames.TIMES);}
"/"					{ return symbol(TokenNames.DIVIDE);}
"\("				{ return symbol(TokenNames.LPAREN);}
"\)"				{ return symbol(TokenNames.RPAREN);}
"\["				{ return symbol(TokenNames.LBRACK);}
"\]"				{ return symbol(TokenNames.RBRACK);}
"\{"				{ return symbol(TokenNames.LBRACE);}
"\}"				{ return symbol(TokenNames.RBRACE);}
"nil"				{ return symbol(TokenNames.NIL);}
","					{ return symbol(TokenNames.COMMA);}
"\."				{ return symbol(TokenNames.DOT);}
";"					{ return symbol(TokenNames.SEMICOLON);}
"<"					{ return symbol(TokenNames.LT);}
">"					{ return symbol(TokenNames.GT);}
"="					{ return symbol(TokenNames.EQ);}
":="				{ return symbol(TokenNames.ASSIGN);}	
{INTEGER}			{ return (Integer.parseInt(yytext()) > 32767) ? symbol(TokenNames.ERROR) : symbol(TokenNames.INT, new Integer(yytext()));}
{STRING}			{ return symbol(TokenNames.STRING, yytext().substring(1, yytext().length() - 1));}
{ID}				{ return symbol(TokenNames.ID, new String( yytext()));}
{COMMENT}		    { /* just skip what was found, do nothing */ }
{WhiteSpace}		{ /* just skip what was found, do nothing */ }
<<EOF>>				{ return symbol(TokenNames.EOF);}
{INVALIDCOMMENT}    { return symbol(TokenNames.ERROR);}
{ERRORCATCHER}		{ return symbol(TokenNames.ERROR);}
}