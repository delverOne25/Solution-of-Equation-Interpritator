/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecxeptions;

/**
 *
 * @author danii
 */
public class LexemException extends Exception{
 
    private int pos;

    public int getNumber(){return pos;}
    public  LexemException(String message, int num){
     
        super(message);
        pos=num;
    }
    @Override
    public String getMessage(){
        return super.getMessage()+"  |  позиция в примере "+pos;
    }
}

