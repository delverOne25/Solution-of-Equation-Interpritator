/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import ecxeptions.LexemException;
import ecxeptions.UnknowOperationException;
import soe.StackSOE;

/**
 *
 * @author danii
 */
public class BuilderCommannd {
    private final static String ADD="+",
            SUB="-",
            MUL="*",
            DIV="/",
            BLOCK_BEG="(",
            BLOCK_END=")";
    
    public static Command factory(String command, StackSOE stack) throws UnknowOperationException{
        Command com=null;
        switch(command){
            case ADD :
                com=new Add(stack);
                break;
            case SUB:
                com=new Sub(stack);
                break;
            case MUL:
                com=new Mul(stack);
                break;
            case DIV:
                com=new Div(stack);
                break;
            case BLOCK_BEG:
                com=new Command(stack){
                         @Override  public void call(String command) throws LexemException {}
                };
                 break;
            case BLOCK_END:
                com=new Command(stack){
                         @Override  public void call(String command) throws LexemException {}
                };
                break;
            default:
                throw new UnknowOperationException("Не известная математическая операция < "+command+" >");
        }
        return com;
    }
}
