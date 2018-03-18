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
public abstract class Command {
    StackSOE stack;
    public Command(StackSOE stack){
        this.stack=stack;
    }
    abstract public void call(String command)throws LexemException;
}
