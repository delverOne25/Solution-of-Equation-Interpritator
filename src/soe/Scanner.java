/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soe;

import ecxeptions.LexemException;
import com.sun.xml.internal.fastinfoset.util.CharArray;
import java.util.Arrays;
import lexem.LexBracket;
import lexem.Lexem;
import lexem.LexOperand;

/**
 *
 * @author danii
 */
public class Scanner {
    
    private String code;
    private static char numbers[]=new char[]{'0','1','2','3','4','5','6','7','8','9','.'};
    private static char[] lexem = new char[]{'-','+','*','/','=','[',']'};
    Scanner(String text){
        code=text;
    
    }
    private boolean findLexem(final char s){
        for(char c : lexem)
            if(c==s)
                return true;
        return false;
    }
    private boolean findNum(final char s){
        for(char c : numbers)
            if(c==s)
                return true;
        return false;
    }
    private boolean findBracket(final char s){
        return (s==')' || s=='(');
    }
    public Lexem[] readLex()throws LexemException{
        char s=code.charAt(0);       // очередной символ
        if(findLexem(s) && s!='-')
            throw errorParse(s, 0);
        String lex="";   // лексема цифры или значения
        int length=code.length();
        int i;        // для итерации по символам
        int spaces=0; // для подсчета пробелов
        int brackets=0; // для подсчета скобок
        int j=0;      // для итерации по лексемам
        boolean isVal=true;// флаг начала последовательности цифр

        Lexem[] lexems=new Lexem[length+1];

        for(i=0; i<length;i++){
            s=code.charAt(i);
            if(findNum(s)){
                lex+=s;
                if(isVal)
                    isVal=false;

            } else if(s=='-' && lex.length()==0 ){
                
                lex+=s;
            }else if(findBracket(s)){
                if(lex.equals("-")){
                    lexems[j++]=lexBuild(lex,i-1-spaces);                                  
                } else if(lex.length()>0)
                    if(s==')')
                        lexems[j++]=lexBuild(lex,i-lex.length()-spaces);
                lexems[j++]=lexBuild(s+"",i);
                
                lex="";
                spaces=0;
                isVal=true;
            }
            else if(findLexem(s) && lex.length()>0 ){
                if(lex.equals("-") || lex.equals("."))
                    throw errorParse(s,i);
                lexems[j++]=lexBuild(lex,i-lex.length()-spaces);
                lexems[j++]=lexBuild(s+"",i);
                lex="";
                spaces=0;
                isVal=true;
            }
            else if(s==' ' ){
                if(lex.length()>0)
                {
                    spaces++;
                    if(!isVal)
                        throw errorParse(s,i);
                }
                          
            }else if(findLexem(s) && (lexems[j-1].getType()<4)){
                lexems[j++]=lexBuild(s+"",i);
                spaces=0;
            }else {
                 throw errorParse(s,i);
               
            }
            
        }
        if(lex.length()>0)
            lexems[j++]=lexBuild(lex,i-lex.length()-spaces);
   
        return Arrays.copyOfRange(lexems, 0, j);
    }
    private LexemException errorParse(char s,int i){
         int startPos=i-12;
         return new LexemException("Не ожидалось прочитать \" "+s+" \" "+"  в "
                    +code.substring(startPos>0?startPos:0,i)+" [^ "+s+" ],  в позиции "+i,i);
    }
    private Lexem lexBuild(String val,int pos){
        if(val.matches("^-?[0-9\\.]+$")){
            return new lexem.LexFloat(Double.parseDouble(val),pos);
        } else if(val.matches("\\(|\\)")){
            return new LexBracket(val.charAt(0),pos);}
          else if(val.length()==1)
            return new LexOperand(val.charAt(0),pos);
        return null;
    }
}
