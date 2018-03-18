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
public class LexFloat implements Lexem {
    private double val;
    private int pos;
    public LexFloat(double val, int pos){
        this.val=val;
        this.pos=pos;
    }
    @Override
    public Object getVal() {
        return this.val;
    }

    @Override
    public short getType() {
        return 100;
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
