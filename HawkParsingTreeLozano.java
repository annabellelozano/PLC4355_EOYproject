// Written By: Annabelle Lozano

import java.io.IOException;

public class HawkParsingTreeLozano
{
   static boolean inDecl_Stmt = false;

    // Rule 01: PROGRAM -> program DECL_SEC begin STMT_SEC end; | program begin STMT_SEC end;
   public static void Program() throws IOException 
   {
        System.out.println("PROGRAM");
        if(HawkMainLozano.nextToken != HawkTokensLozano.getProgram())
        {
            System.out.printf("Error! Expected program at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
        HawkMainLozano.lex();
        if(HawkMainLozano.nextToken == HawkTokensLozano.getBegin())
        {
            HawkMainLozano.lex();
            Stmt_Sec();
        }
        else
        {
            Decl_Sec();
            if(HawkMainLozano.nextToken == HawkTokensLozano.getBegin())
            {
                HawkMainLozano.lex();
                Stmt_Sec();
            }
            else
            {
                System.out.printf("Error! Expected begin at line %d \n", HawkMainLozano.lineCount);
                System.exit(0);
            }

        }
        
        if(HawkMainLozano.nextToken == HawkTokensLozano.getEnd())
        {
            HawkMainLozano.lex();
            System.exit(0);
        }
        else
        {
            System.out.println(HawkMainLozano.nextToken);
            System.out.printf("Error! Expected end at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
   }

    // Rule 02: DECL_SEC -> DECL | DECL DECL_SEC
    private static void Decl_Sec() throws IOException
    {
       // if(nextToken != HawkTokensLozano.getDeclSec())
       // {
       //     System.out.printf("Error! Expected DECL_SEC at line %d \n", lineCount);
       //     System.exit(0);
       // }
        System.out.println("DECL_SEC");
        inDecl_Stmt = true;
        Decl();
        while(HawkMainLozano.nextToken != HawkTokensLozano.getBegin())
        {
            Decl_Sec();
        }   
        // Checks to see if inside a declaration section for symbol table.
        inDecl_Stmt = false;
           
    }
    
    // Rule 03: DECL -> int ID_LIST : TYPE;
    public static void Decl() throws IOException
    {
        System.out.println("DECL");
        ID_list();  
        
            if(HawkMainLozano.nextToken != HawkTokensLozano.getColon())
            {
                System.out.printf("Error!: Expected : at line %d \n", HawkMainLozano.lineCount);
                System.exit(0);
            }
                else
            {
                HawkMainLozano.lex();
                Type();
                if(HawkMainLozano.nextToken == HawkTokensLozano.getSemicolon())
                {
                    HawkMainLozano.lex();
                    return;
                }
                else
                {
                    System.out.printf("Error!: Expected ; at line %d \n", HawkMainLozano.lineCount);
                    System.exit(0);
                }
            }
            }

    // Rule 04: ID_LIST -> ID | ID , ID_LIST
    public static void ID_list() throws IOException
    {
        System.out.println("ID_LIST");
        ID();
        while(HawkMainLozano.nextToken == HawkTokensLozano.getComma())
        {
            HawkMainLozano.lex();
            ID_list();
        }
    }

    // Rule 05: ID -> (_ | a | b | … | z | A | … | Z) (_ | a | b | … | z | A | … | Z | 0 | 1 | … | 9)*
    public static void ID() throws IOException
    {
        String varName = new String(HawkMainLozano.lexeme, 0, HawkMainLozano.lexLen);

        // Check to see if varName is a reserved word
        if(HawkMainLozano.reservedWords.containsKey(varName))
        {
            System.out.printf("Error!: %s is a reserved word at line %d \n", varName, HawkMainLozano.lineCount);
            System.exit(0);
        }
        // Check to see if Identifier is in Symbol Table
        if(inDecl_Stmt)
        {
            if(!HawkMainLozano.symbolTable.containsKey(varName))
            {
                HawkMainLozano.symbolTable.put(varName, "var");
            }
            else
            {
                System.out.printf("Error!: Variable %s already declared at line %d \n", varName, HawkMainLozano.lineCount);
                System.exit(0);
            }
        }
        if(HawkMainLozano.symbolTable.containsKey(varName))
        {
            HawkMainLozano.lex();
            return;
        }
        else
        {
            System.out.printf("Error! Variable %s is not declared at line %d \n", varName, HawkMainLozano.lineCount);
            System.exit(0);
        }
    }

    // Rule 06: STMT_SEC -> STMT | STMT STMT_SEC
    private static void Stmt_Sec() throws IOException
    {
        System.out.println("STMT_SEC");
        Stmt();
        while(HawkMainLozano.nextToken != HawkTokensLozano.getEnd() && HawkMainLozano.nextToken != HawkTokensLozano.getElse() && HawkMainLozano.nextToken != HawkTokensLozano.getLoop() && HawkMainLozano.nextToken != HawkTokensLozano.getSemicolon())
        {
            Stmt_Sec();
        }
    }

    // Rule 07: STMT -> ASSIGN | IFSTMT | WHILESTMT | INPUT | OUTPUT
    private static void Stmt() throws IOException
    {
        System.out.println("STMT");
        if(HawkMainLozano.nextToken == HawkTokensLozano.getIf())
        {
            IFstmt();
        }
        else if(HawkMainLozano.nextToken == HawkTokensLozano.getWhile())
        {
            WhileStmt();
        }
        else if(HawkMainLozano.nextToken == HawkTokensLozano.getInput())
        {
            Input();
        }
        else if(HawkMainLozano.nextToken == HawkTokensLozano.getOutput())
        {
            Output();
        }
        else if(HawkMainLozano.nextToken == HawkMainLozano.IDENT)
        {
            Assign();
        }   
    }

    // try case statments because if and else statments were not working
    

    // Rule 08: ASSIGN -> ID := EXPR ;
    private static void Assign() throws IOException
    {
        System.out.println("ASSIGN");
        ID();
        if(HawkMainLozano.nextToken == HawkTokensLozano.getAssign())
        {
            HawkMainLozano.lex();
            Expr();
        }
        if(HawkMainLozano.nextToken == HawkTokensLozano.getSemicolon())
        {
            HawkMainLozano.lex();
            return;
        }
        else
        {
            System.out.printf("Error! Expected ; at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
    }
    
    // Rule 09: IFSTMT -> if COMP then STMT_SEC end if ; | if COMP then STMT_SEC else STMT_SEC end if ;
    private static void IFstmt() throws IOException
    {
        System.out.println("IFSTMT");
            if(HawkMainLozano.nextToken != HawkTokensLozano.getIf())
            {
                System.out.printf("Error! Expected if at line %d \n", HawkMainLozano.lineCount);
                System.exit(0);
            }
            HawkMainLozano.lex();
            Comp();
            if(HawkMainLozano.nextToken == HawkTokensLozano.getThen())
            {
                HawkMainLozano.lex();
                Stmt_Sec();
                if(HawkMainLozano.nextToken == HawkTokensLozano.getElse())
                {
                    HawkMainLozano.lex();
                    Stmt_Sec();
                }
                if(HawkMainLozano.nextToken == HawkTokensLozano.getEnd())
                {
                    HawkMainLozano.lex();
                    if(HawkMainLozano.nextToken == HawkTokensLozano.getIf())
                    {
                        HawkMainLozano.lex();
                        if(HawkMainLozano.nextToken == HawkTokensLozano.getSemicolon())
                        {
                            HawkMainLozano.lex();
                            return;
                        }
                        else
                        {
                            System.out.printf("Error! Expected ; at line %d \n", HawkMainLozano.lineCount);
                            System.exit(0);
                        }
                    }
                    else
                    {
                        System.out.printf("Error! Expected end if at line %d \n", HawkMainLozano.lineCount);
                        System.exit(0);
                    }
                }
            }
            else
            {
                System.out.printf("Error! Expected then at line %d \n", HawkMainLozano.lineCount);
                System.exit(0);
            }
    }

    // Rule 10: WHILESTMT -> while COMP loop STMT_SEC end loop ;
    private static void WhileStmt() throws IOException
    {
        System.out.println("WHILESTMT");
        if(HawkMainLozano.nextToken != HawkTokensLozano.getWhile())
        {
            System.out.printf("Error! Expected while at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
        HawkMainLozano.lex();
        Comp();
        if(HawkMainLozano.nextToken == HawkTokensLozano.getLoop())
        {
            HawkMainLozano.lex();
            Stmt_Sec();
            if(HawkMainLozano.nextToken == HawkTokensLozano.getEnd())
            {
                HawkMainLozano.lex();
                if(HawkMainLozano.nextToken == HawkTokensLozano.getLoop())
                {
                    HawkMainLozano.lex();
                    if(HawkMainLozano.nextToken == HawkTokensLozano.getSemicolon())
                    {
                        HawkMainLozano.lex();
                        return;
                    }
                    else
                    {
                        System.out.printf("Error! Expected ; at line %d \n", HawkMainLozano.lineCount);
                        System.exit(0);
                    }
                }
                else
                {
                    System.out.printf("Error! Expected end loop at line %d \n", HawkMainLozano.lineCount);
                    System.exit(0);
                }
            }
            else
            {
                System.out.printf("Error! Expected end at line %d \n", HawkMainLozano.lineCount);
                System.exit(0);
            }
        }
        else
        {
            System.out.printf("Error! Expected loop at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
    }

    // Rule 11: INPUT -> input ID_LIST ;
    private static void Input() throws IOException
    {
        System.out.println("INPUT");
        if(HawkMainLozano.nextToken != HawkTokensLozano.getInput())
        {
            System.out.printf("Error! Expected input at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
        HawkMainLozano.lex();
        ID_list();
        if(HawkMainLozano.nextToken == HawkTokensLozano.getSemicolon())
        {
            HawkMainLozano.lex();
            return;
        }
        else
        {
            System.out.printf("Error! Expected ; at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
    }

    // Rule 12: OUTPUT -> output ID_LIST | output NUM ;
    private static void Output() throws IOException
    {
        System.out.println("OUTPUT");
  
        if(HawkMainLozano.nextToken != HawkTokensLozano.getOutput())
        {
            System.out.printf("Error! Expected output at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
        HawkMainLozano.lex();

        if(HawkMainLozano.nextToken == HawkMainLozano.IDENT)
        {
            ID_list();
        }
        else if(HawkMainLozano.nextToken == HawkMainLozano.INT_LIT)
        {
            Num();
        }
        if(HawkMainLozano.nextToken == HawkTokensLozano.getSemicolon())
        {
            HawkMainLozano.lex();
            return;
        }
        else
        {
            System.out.printf("Error! Expected ; at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
    }

    // Rule 13: EXPR -> FACTOR | FACTOR + EXPR | FACTOR - EXPR
    private static void Expr() throws IOException
    {
        System.out.println("EXPR");
        Factor();
        if(HawkMainLozano.nextToken == HawkTokensLozano.getPlus() || HawkMainLozano.nextToken == HawkTokensLozano.getMinus())
        {
            HawkMainLozano.lex();
            Expr();
        }
    }

    // Rule 14: FACTOR -> OPERAND | OPERAND * FACTOR | OPERAND / FACTOR
    private static void Factor() throws IOException
    {
        System.out.println("FACTOR");
        Operand();
        while(HawkMainLozano.nextToken == HawkTokensLozano.getMultiply() || HawkMainLozano.nextToken == HawkTokensLozano.getDivide())
        {
            HawkMainLozano.lex();
            Factor();
        }
    }

    // Rule 15: OPERAND -> NUM | ID | (EXPR)
    private static void Operand() throws IOException
    {
        System.out.println("OPERAND");
        if(HawkMainLozano.nextToken == HawkMainLozano.IDENT)
        {
            ID();
        }
        else if(HawkMainLozano.nextToken == HawkMainLozano.INT_LIT)
        {
            Num();
        }
        else if(HawkMainLozano.nextToken == HawkTokensLozano.getLeftParen())
        {
            HawkMainLozano.lex();
            Expr();
            if(HawkMainLozano.nextToken == HawkTokensLozano.getRightParen())
            {
                HawkMainLozano.lex();
                return;
            }
            else
            {
                System.out.printf("Error! Expected ) at line %d \n", HawkMainLozano.lineCount);
                System.exit(0);
            }
        }
        else
        {
            System.out.printf("Error! Expected ID, NUM, or ( at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
    }

    // Rule 16: NUM -> (0 | 1 | ... | 9)+ [.(0 | 1 | … | 9)+]
    private static void Num() throws IOException
    {
        HawkMainLozano.lex();
        //System.out.println("hello");
        //System.out.println(HawkMainLozano.nextToken);
    }

    // Rule 17: COMP -> ( OPERAND = OPERAND ) | ( OPERAND <> OPERAND ) | ( OPERAND > OPERAND ) | ( OPERAND < OPERAND )
    private static void Comp() throws IOException
    {
        System.out.println("COMP");
        if(HawkMainLozano.nextToken == HawkTokensLozano.getLeftParen())
        {
            HawkMainLozano.lex();
            Operand();
            if(HawkMainLozano.nextToken == HawkTokensLozano.getEquals() || HawkMainLozano.nextToken == HawkTokensLozano.getNotEqual() || HawkMainLozano.nextToken == HawkTokensLozano.getGreaterThan() || HawkMainLozano.nextToken == HawkTokensLozano.getLessThan())
            {
                HawkMainLozano.lex();
                Operand();
                if(HawkMainLozano.nextToken == HawkTokensLozano.getRightParen())
                {
                    HawkMainLozano.lex();
                    return;
                }
                else
                {
                    System.out.printf("Error! Expected ) at line %d \n", HawkMainLozano.lineCount);
                    System.exit(0);
                }
            }
            else
            {
                System.out.printf("Error! Expected =, <>, >, or < at line %d \n", HawkMainLozano.lineCount);
                System.exit(0);
            }
        }
        else
        {
            System.out.printf("Error! Expected ( at line %d \n", HawkMainLozano.lineCount);
            System.exit(0);
        }
    }

    // Rule 18: TYPE -> int | float | double
    private static void Type() throws IOException
    {
        HawkMainLozano.lex();
    }
}
