//Joseph Curtis
//891970816

package lexer;

import java.io.*;
import java.util.*;

public class Lexer {

    public static enum Symbols {
        IF, FOR, WHILE, PROC, RETURN, INT, ELSE, DO, BREAK, END, ASSIGN, ADD_OP,
        SUB_OP, MUL_OP, DIV_OP, MOD_OP, GT, LT, GE, LE, INC, LP, RP, LB, RB, OR,
        AND, EE, NEG, COMMA, SEMI;
    }
    
    public static Map<String, Symbols> tokens = new HashMap<>();  //haven't used these in ages, s/o Prof. Shah
    
    private static boolean isInvalidID(String word){        //error case
        return word.length() > 1 && Character.isDigit(word.charAt(0)) && Character.isAlphabetic(word.charAt(1));
    }
    
    private static boolean isAlphaNum(char c){      //finding the english opertations: 'if', 'else', etc.
        return Character.isLetterOrDigit(c);
    }
    
    private static boolean isDoubleChar(String scan, int i){
        return (scan.charAt(i) == '=' ||            //all req double chars end w/ + or =, so just find the start
                scan.charAt(i) == '>' ||
                scan.charAt(i) == '<' ||
                scan.charAt(i) == '+') && 
                (i < scan.length() - 1) && (scan.charAt(i + 1) == '=' || scan.charAt(i + 1) == '+');    //find end + or =
    }           //adding future &&, ||, etc. cases would go here
    
    private static boolean isSpecialChar(char c){
        return c == '=' ||
               c == '+' ||
               c == '-' ||
               c == '*' ||
               c == '/' ||
               c == '%' ||
               c == '>' ||
               c == '<' ||
               c == '(' ||
               c == ')' ||
               c == '{' ||
               c == '}' ||
               c == '|' ||
               c == '&' ||
               c == '!' ||
               c == ',' ||
               c == ';';
    }
    
    public static void getToken(String scan, String word, ArrayList list, int i){
        if (word.length() > 0){
            if (tokens.containsKey(word)){
                System.out.println(tokens.get(word));
            } 
            
            else {
                if (!Character.isDigit(word.charAt(0)))
                    System.out.println("IDENT");
                else 
                    System.out.println("INT_CONST");
            }
        }
        
        int listSize = list.size();
            if (listSize > 0){
                System.out.println(tokens.get(list.get(0)));
                list.clear();
            }
        }
    
    public static void initTokens(){
        tokens.put("if", Symbols.IF);
        tokens.put("for", Symbols.FOR);
        tokens.put("while", Symbols.WHILE);
        tokens.put("procedure", Symbols.PROC);
        tokens.put("return", Symbols.RETURN);
        tokens.put("int", Symbols.INT);
        tokens.put("else", Symbols.ELSE);
        tokens.put("do", Symbols.DO);
        tokens.put("break", Symbols.BREAK);
        tokens.put("end", Symbols.END);
        tokens.put("=", Symbols.ASSIGN);
        tokens.put("+", Symbols.ADD_OP);
        tokens.put("-", Symbols.SUB_OP);
        tokens.put("*", Symbols.MUL_OP);
        tokens.put("/", Symbols.DIV_OP);
        tokens.put("%", Symbols.MOD_OP);
        tokens.put(">", Symbols.GT);
        tokens.put("<", Symbols.LT);
        tokens.put(">=", Symbols.GE);
        tokens.put("<=", Symbols.LE);
        tokens.put("++", Symbols.INC);
        tokens.put("(", Symbols.LP);
        tokens.put(")", Symbols.RP);
        tokens.put("{", Symbols.LB);
        tokens.put("}", Symbols.RB);
        tokens.put("|", Symbols.OR);
        tokens.put("&", Symbols.AND);
        tokens.put("==", Symbols.EE);
        tokens.put("!", Symbols.NEG);
        tokens.put(",", Symbols.COMMA);
        tokens.put(";", Symbols.SEMI);
    }
    
    public static void Tokenize(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        
        while(scan.hasNextLine()){          //iterate through given txt file
            String sc = scan.nextLine();
            String word = "";
            ArrayList<String> list = new ArrayList<>();         //add txt to list for decryption
            
            for(int i = 0; i < sc.length(); i++){             
                if (isAlphaNum(sc.charAt(i))){                 //checking for words
                    word += sc.charAt(i);
                    if (i == sc.length() - 1) 
                        getToken(sc, word, list, i);
                }
                
                else if (isDoubleChar(sc, i)){              //checking for double chars
                    String full = String.valueOf(sc.charAt(i)) + String.valueOf(sc.charAt(i + 1));
                    list.add(full);
                    i++;
                }
                
                else if (isSpecialChar(sc.charAt(i))){      //checking for special chars
                    getToken(sc, word, list, i);
                    String specialChar;
                    specialChar = String.valueOf(sc.charAt(i));
                    System.out.println(tokens.get(specialChar));
                    word = "";
                }
                
                else {
                    if (isInvalidID(word)){             //error case
                        System.out.println("SYNTAX ERROR: INVALID IDENTIFIER NAME");
                        break;
                    }
                    getToken(sc, word, list, i);
                    word = "";
                }
            }
        }
        scan.close();
    }
    
//   public static void main(String[] args) throws FileNotFoundException {
//       initTokens();
//       Tokenize("testcase.txt");
//   }
}
