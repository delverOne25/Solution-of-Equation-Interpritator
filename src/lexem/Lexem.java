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
public interface Lexem<T> {
    public T getVal();
    public short getType();
    public int getPos();
}
