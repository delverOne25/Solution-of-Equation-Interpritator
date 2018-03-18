/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import ecxeptions.LexemException;
import soe.StackSOE;

/**
 *
 * @author danii
 */
public class Sub extends Command{
      public Sub(StackSOE stack){
        super(stack);
    }
    @Override
    public void call(String command) throws LexemException{
        Object a1=stack.pop();
        Object a2=stack.pop();
        if(a1==null || a2== null)
            throw new LexemException("Не правильно составленно дерево кода",stack.getPos());
       stack.push( (double)a2-(double)a1);
    }
}