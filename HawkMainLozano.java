import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
public class HawkMainLozano
{
    public static char[] lexeme = new char[100];
    public static int lexLen;
    public static char nextChar;
    private static int CharClass;
    private static BufferedReader br;
    public static int nextToken;
    private static final int EOF = -1;
    public static int lineCount = 1;

    /*private static char peek()
    {
        return currentChar + 1 < input.length() ? input.charAt(currentPosition + 1) : '\0';
    }*/

    static HashMap<String, Integer> reservedWords = new HashMap<>();
    public static HashMap<String, String> symbolTable = new HashMap<>(); 


    private static final int LETTER = 0;
    private static final int DIGIT = 1;
    private static final int UNKNOWN = 2;
    private static final int UNDERSCORE = 3;
    public static final int INT_LIT = 4;
    public static final int IDENT = 5;

    public static void main(final String[] args)
    {
        reservedWords.put("program", HawkTokensLozano.getProgram()); // Hash Map could not find the get Functions in HawkTokensLozano.java (Because they were set to private)
        reservedWords.put("begin", HawkTokensLozano.getBegin());
        reservedWords.put("end", HawkTokensLozano.getEnd());
        reservedWords.put("if", HawkTokensLozano.getIf());
        reservedWords.put("then", HawkTokensLozano.getThen());
        reservedWords.put("else", HawkTokensLozano.getElse());
        reservedWords.put("input", HawkTokensLozano.getInput());
        reservedWords.put("output", HawkTokensLozano.getOutput());
        reservedWords.put("myint", HawkTokensLozano.getMyInt()); // INT
        reservedWords.put("while", HawkTokensLozano.getWhile());
        reservedWords.put("loop", HawkTokensLozano.getLoop());

        //String filepath = " ";
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the file path of the input you wish to scan: ");
        String filepath = sc.nextLine();
        File file = new File(filepath); 
        System.out.println();

        // Add a \ in the filepath if a \ exists
        addslash(filepath);
        
        try {
            br = new BufferedReader(new FileReader(file));
            getCharClass();
            while (CharClass != EOF) 
            {
                do
                {
                    lex();
                    HawkParsingTreeLozano.Program();
                }while(nextToken != -1);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            try 
            {
                if (br != null) 
                {
                    br.close();
                }
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

   private static void getCharClass() throws IOException // Determines the Char class
   {
        int nextCharInt = br.read();
        if (nextCharInt != -1) 
        {
            nextChar = (char) nextCharInt;
            if (Character.isLetter(nextChar)) 
            {
                CharClass = LETTER;
            } 
            else if (Character.isDigit(nextChar)) 
            {
                CharClass = DIGIT;
            }
            else if(nextChar == '_') // Add underscore to the condition
            {
                CharClass = UNDERSCORE;
            }
            else 
            {
                CharClass = UNKNOWN;
            }
        } 
        else 
        {
            CharClass = -1;
        }
     
   }

   private static void getNonBlank() throws IOException // Ignores Whitespace
   {
    while(Character.isWhitespace(nextChar))
    {
        // Counts the number of lines
        if(nextChar == '\n')
        {
           // isNewLine = true;
            lineCount++;
        }
        getCharClass();
    }
   }

   private static void addChar() // Adds current character to char array.
   {
        if (lexLen <= 98) 
        {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = '\0';
        } 
        else 
        {
            System.out.println("Error!! - The lexeme is too long!");
        }
   }

   private static int lookUp(int nextChar) throws IOException
   {
    switch((char)nextChar)
    {
        case '+':
            addChar();
            nextToken = HawkTokensLozano.getPlus();
            break;

        case ';':
            addChar();
            nextToken = HawkTokensLozano.getSemicolon();
            break;

        case ',':
            addChar();
            nextToken = HawkTokensLozano.getComma();
            break;

        case ':':
            addChar();
            //getCharClass();
            if (peekNextChar() == '=') //LOOK AT THIS LINE
            {
                getCharClass();
                addChar();
                nextToken = HawkTokensLozano.getAssign();
            } 
            else 
            {
                getCharClass();
                nextToken = HawkTokensLozano.getColon();
            }
            break;

        case '(':
            addChar();
            nextToken = HawkTokensLozano.getLeftParen();
            break;

        case ')':
            addChar();
            nextToken = HawkTokensLozano.getRightParen();
            break;

        case '-':
            addChar();    
            nextToken = HawkTokensLozano.getMinus();
            break;

        case '*':
            addChar();
            nextToken = HawkTokensLozano.getMultiply();
            break;

        case '/':
            addChar();
            nextToken = HawkTokensLozano.getDivide();
            break;

        case '=':
            addChar();  
            nextToken = HawkTokensLozano.getEquals();
            break;

        case '>':
            addChar();
            nextToken = HawkTokensLozano.getGreaterThan();
            break;
            
        /*case '<':
            addChar();
            nextToken = HawkTokensLozano.getLessThan();
            break;*/
        
        case '<':
            addChar(); 
            //getCharClass();
            //if (nextChar == '>')
            
            if (peekNextChar() == '>') // will make less than
            {
                getCharClass();
                addChar();
                //System.out.println("Here");
                //HawkTokensLozano.add(new TokenTokenType.NOT_EQUAL, "<>"));
                nextToken = HawkTokensLozano.getNotEqual();
            } 
            else
            {
                //System.out.println("Here");
                getCharClass();
                nextToken = HawkTokensLozano.getLessThan();
            }
            break;

        /*case '<':
            addChar(); 
            //getCharClass();
            //if (nextChar == '>')

            if (nextChar == HawkTokensLozano.getGreaterThan())
            {
                getCharClass();
                addChar();
                nextToken = HawkTokensLozano.getNotEqual();
            } else 
            {
                nextToken = HawkTokensLozano.getLessThan();
            }

        case '<>':
            nextToken = HawkTokensLozano.getLessThan() + nextToken = HawkTokensLozano.getGreaterThan()
            break;*/
        

        case '_':
            getCharClass();
            addChar();
            nextToken = HawkTokensLozano.getUnderscore();
            break;
        default:
            addChar();
            nextToken = EOF;
            break;
            
    }
    return nextToken;
   }
   // Function to peek at the next character because comparing nextChar would not work for some reason.
   private static char peekNextChar() throws IOException 
   {
    br.mark(1); // Mark the current position in the stream
    int nextCharInt = br.read(); // Read the next character
    br.reset(); // Reset the stream to the marked position
    return (char) nextCharInt;
}


   public static int lex() throws IOException
   {
    lexLen = 0;
    getNonBlank(); // Skips whitespace
    switch (CharClass) 
    {
        // Parse identifers 
        case LETTER:
        case UNDERSCORE: // Add underscore to the condition
            addChar();
            getCharClass();
            while(CharClass == DIGIT || CharClass == LETTER || CharClass == UNDERSCORE) // Add underscore to the condition
            {
                addChar();
                getCharClass();
            }  
            // Parse reserved words 
            if(reservedWords.containsKey(new String(lexeme, 0, lexLen)))
            {
                nextToken = reservedWords.get(new String(lexeme, 0, lexLen));
            }
            else
            {
                nextToken = IDENT;
            }   
            break;

        // Parse literals 
        case DIGIT:
            addChar();
            getCharClass();
            while(CharClass == DIGIT)
            {
                addChar();
                getCharClass();
            } 
            nextToken = INT_LIT;
            break;

        // Parse others
        case UNKNOWN:
            lookUp(nextChar);
            getCharClass();
            break;

        case EOF:
            nextToken = EOF;
            lexeme[0] = 'E';
            lexeme[1] = 'O';
            lexeme[2] = 'F';
            lexeme[3] = 0;
            break;
    }
    //System.out.println("The next token is: " + nextToken + ", The next lexeme is " + new String(lexeme, 0, lexLen));
    return nextToken;
   }

   // Function to add a \ in the filepath if a \ exists
   public static String addslash(String filepath) 
   {
    StringBuilder newFilePath = new StringBuilder();
    for(int i = 0; i < filepath.length(); i++) {
        if(filepath.charAt(i) == '\\') {
            newFilePath.append("\\\\");
        } else {
            newFilePath.append(filepath.charAt(i));
        }
    }
    return newFilePath.toString();
}
}
