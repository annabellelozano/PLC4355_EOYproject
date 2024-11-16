public class HawkTokensLozano
{
    // defining keywords aka tokens : reserved words
    public static final int PROGRAM = 0000; // reserved word
    public static final int BEGIN = 0001; // reserved word
    public static final int END = 0010; // reserved word
    public static final int IF = 0011; // reserved word
    public static final int THEN = 0100; // reserved word
    public static final int ELSE = 0101; // reserved word
    public static final int INPUT = 0110; // reserved word
    public static final int OUTPUT = 0111; // reserved word
    public static final int MYINT = 1000; // reserved word -- INT
    public static final int WHILE = 1001; // reserved word
    public static final int LOOP = 1010; // reserved word
    public static final int END_LOOP = 1009;
    private static final int MYFLOAT = 1013; // -- FLOAT
    private static final int MYDOUBLE = 1014; // -- DOUBLE
    private static final int DECL_SEC = 1015;
    private static final int DECL = 1016;
    private static final int ID_LIST = 1017;
    private static final int ID = 1018;
    private static final int STMT_SEC = 1019;
    private static final int STMT = 1020;
    private static final int IFSTMT = 1021;
    private static final int WHILESTMT = 1022;
    private static final int EXPR = 1023;
    private static final int FACTOR = 1024;
    private static final int OPERAND = 1025;
    private static final int NUM = 1026;
    private static final int COMP = 1027;
    private static final int TYPE = 1028;

    // operators 
    private static final int ASSIGN = 2000; // :=
    private static final int SEMICOLON = 2001; // ;
    private static final int COMMA = 2002; // ,
    private static final int COLON = 2003; // :
    private static final int LEFT_PAREN = 2004; // (
    private static final int RIGHT_PAREN = 2005; // )
    private static final int PLUS = 2006; // +
    private static final int MINUS = 2007; // -
    private static final int MULTIPLY = 2008; // *
    private static final int DIVIDE = 2009; // /
    private static final int EQUALS = 2010; // =
    private static final int NOT_EQUAL = 2011; // <>
    private static final int GREATER_THAN = 2012; // >
    private static final int LESS_THAN = 2013; // <
    private static final int UNDERSCORE = 2014; // _

    // Getters for each of the defined 
    public static int getProgram() 
    {
        return PROGRAM;
    }
    public static int getBegin() 
    {
        return BEGIN;
    }

    public static int getEnd() 
    {
        return END;
    }

    public static int getIf() 
    {
        return IF;
    }

    public static int getThen() 
    {
        return THEN;
    }

    public static int getElse() 
    {
        return ELSE;
    }

    public static int getWhile() 
    {
        return WHILE;
    }

    public static int getLoop() 
    {
        return LOOP;
    }

    public static int getEndLoop() 
    {
        return END_LOOP;
    }

    public static int getInput() 
    {
        return INPUT;
    }

    public static int getOutput() 
    {
        return OUTPUT;
    }

    public static int getMyInt() 
    {
        return MYINT;
    }

    public static int getMyFloat() 
    {
        return MYFLOAT;
    }

    public static int getMyDouble() 
    {
        return MYDOUBLE;
    }

    public static int getAssign() 
    {
        return ASSIGN;
    }

    public static int getSemicolon() 
    {
        return SEMICOLON;
    }

    public static int getComma() 
    {
        return COMMA;
    }

    public static int getColon() 
    {
        return COLON;
    }

    public static int getLeftParen() 
    {
        return LEFT_PAREN;
    }

    public static int getRightParen() 
    {
        return RIGHT_PAREN;
    }

    public static int getPlus() 
    {
        return PLUS;
    }

    public static int getMinus() 
    {
        return MINUS;
    }

    public static int getMultiply() 
    {
        return MULTIPLY;
    }

    public static int getDivide() 
    {
        return DIVIDE;
    }

    public static int getEquals() 
    {
        return EQUALS;
    }

    public static int getNotEqual() 
    {
        return NOT_EQUAL;
    }

    public static int getGreaterThan() 
    {
        return GREATER_THAN;
    }

    public static int getLessThan() 
    {
        return LESS_THAN;
    }
    public static int getExpr() 
    {
        return EXPR;
    }

    public static int getFactor() 
    {
        return FACTOR;
    }

    public static int getOperand() 
    {
        return OPERAND;
    }

    public static int getNum() 
    {
        return NUM;
    }

    public static int getComp() 
    {
        return COMP;
    }

    public static int getType() 
    {
        return TYPE;
    }
    public static int getDeclSec() 
    {
        return DECL_SEC;
    }

    public static int getDecl() 
    {
        return DECL;
    }

    public static int getIdList() 
    {
        return ID_LIST;
    }

    public static int getId() 
    {
        return ID;
    }

    public static int getStmtSec() 
    {
        return STMT_SEC;
    }

    public static int getStmt() 
    {
        return STMT;
    }

    public static int getIfStmt() 
    {
        return IFSTMT;
    }

    public static int getWhileStmt() 
    {
        return WHILESTMT;
    }
    public static int getUnderscore()
    {
        return UNDERSCORE;
    }

}
