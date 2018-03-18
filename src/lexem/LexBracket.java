/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexem;

/**
 *
 * @author danii
 */
public class LexBracket  implements Lexem{

    private char val;
    private int pos;
    private boolean closedStatus;
    public  LexBracket(char val, int pos){
        this.val=val;
        this.pos=pos;
        closedStatus=false;
    }
    
    public boolean isClosed(){
        return closedStatus;
    }
    public void toogle(){
        closedStatus=!closedStatus;
    }

 
    
    @Override
    public Object getVal() {
        return this.val;
    }

    @Override
    public short getType() {
        if(val=='(')
            return 2;
        else 
            return 3;
    }

    @Override
    public int getPos() {
        return pos;
    }
    
    @Override
    public String toString(){
        return ""+val;
    }
    
}
