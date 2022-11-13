public interface TokenNames {
    /* terminals */
    public static final int EOF = 0;
    public static final int PLUS = 1;
    public static final int MINUS = 2;
    public static final int TIMES = 3;
    public static final int DIVIDE = 4;
    public static final int LPAREN = 5;
    public static final int RPAREN = 6;
    public static final int NUMBER = 7;
    public static final int ID = 8;
    public static final int INT = 9;
    public static final int CLASS = 10;
    public static final int NIL = 11;
    public static final int ARRAY = 12;
    public static final int WHILE = 13;
    public static final int EXTENDS = 14;
    public static final int RETURN = 15;
    public static final int NEW = 16;
    public static final int IF = 17;
    public static final int STRING = 18;
    public static final int EQ = 19;
    public static final int TYPE_STRING = 20;
    public static final int TYPE_INT = 21;
    public static final int TYPE_VOID = 22;
    public static final int SEMICOLON = 23;
    public static final int LBRACK = 24;
    public static final int RBRACK = 25;
    public static final int LBRACE = 26;
    public static final int RBRACE = 27;
    public static final int COMMA = 28;
    public static final int DOT = 29;
    public static final int ASSIGN = 30;
    public static final int LT = 31;
    public static final int GT = 32;
    public static final int ERROR = 33;
    public static final String[] tokenToTokenName = new String[]{
            "EOF",
            "PLUS",
            "MINUS",
            "TIMES",
            "DIVIDE",
            "LPAREN",
            "RPAREN",
            "NUMBER",
            "ID",
            "INT",
            "CLASS",
            "NIL",
            "ARRAY",
            "WHILE",
            "EXTENDS",
            "RETURN",
            "NEW",
            "IF",
            "STRING",
            "EQ",
            "TYPE_STRING",
            "TYPE_INT",
            "TYPE_VOID",
            "SEMICOLON",
            "LBRACK",
            "RBRACK",
            "LBRACE",
            "RBRACE",
            "COMMA",
            "DOT",
            "ASSIGN",
            "LT",
            "GT",
            "ERROR"
    };
}
