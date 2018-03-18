/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soe;

import ecxeptions.LexemException;
import ecxeptions.UnknowOperationException;
import java.util.Scanner;
import java.util.Stack;
import lexem.Lexem;
import lib.BuilderCommannd;

/**
 *
 * @author danii
 */
public class StackSOE {
    private Stack ss;
    private TreeLiterals tree;
    private int currentPos;
    public StackSOE(/*TreeLiterals tree*/int capacity) {
       // this.tree=tree;
       ss=new Stack();
       ss.ensureCapacity(capacity);
    }
    public void push(double data){
        ss.push(data);
    }
    public Object pop(){
        return ss.pop();
    }
    
    public int getPos(){
        return currentPos;
    }
    
   public  String returnRes() {
     
      if(ss.empty())
          return "Ошибка вычисления, не получилось правильно составить вычисления";
      double res = (double)ss.pop();
      return ""+res;
    }

    void command(Lexem lex) throws UnknowOperationException, LexemException {
        try{
            double d =(double)lex.getVal();
            currentPos=lex.getPos();
            push(d);
        }catch(Exception ex){
            BuilderCommannd.factory(lex.toString(), this).call(null);
        }
    }
    
    
}
