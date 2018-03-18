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
public class LexOperand implements Lexem{
    final private  char  znak;
    private int pos;
     private short type;
    public LexOperand(final char znak,int pos){
        this.znak=znak;
        this.pos=pos;
        switch(znak){
            case '+':
                type=10;
                break;
            case '-':
                type=10;
                break;
            case '*':
                type=12;
                break;
            case '/':
                type=12;
                break;
           
        }
    }
    @Override
    public Object getVal() {
        return znak;
    }

    @Override
    public short getType() {
        return type;
    }

    @Override
    public int getPos() {
        return pos;
    }
    @Override 
    public String toString(){
        return znak+"";
    }
    
}
