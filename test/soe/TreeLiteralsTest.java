/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soe;

import ecxeptions.LexemException;
import ecxeptions.UnknowOperationException;
import lexem.LexFloat;
import lexem.Lexem;
import lexem.LexOperand;
import org.junit.Test;


/**
 *
 * @author danii
 */
public class TreeLiteralsTest {
    public void printCommand(int type, char comm){
        if(type==10 )
            System.out.println("\u001b[34m"+(comm=='+'?"add":"sub")+"\u001b[0m");
        else if(type==12)
            System.out.println("\u001b[34m"+(comm=='*'?"mul":"div")+"\u001b[0m");
    }
    public void printTree(TreeLiterals.Node n) throws UnknowOperationException, LexemException{
  
        if(n.getLChild()!=null && n.getLChild().visited==false){
            n=n.getLChild();         
        }
       else if(n.getRChild()!=null && n.getRChild().visited==false)
            n=n.getRChild();
       else{
             n.visited=true;
             if(n.getLex().getType()>50)
                System.out.println("\u001b[34mpush  "+n.getLex().getVal()+"\u001b[0m");
      
             else if(n.getLex().getType()>4)
             {
                 printCommand(n.getLex().getType(),n.getLex().toString().charAt(0));
             }
                
             TreeLiterals.Node temp=n.getHead();
             if(temp==null || temp.visited)
                 return;
             n=temp;
       }
      printTree(n);
    }
    @Test
    public void main() throws UnknowOperationException, LexemException{
          
       
            Lexem[] lexs =new Lexem[17];
            lexs[0]=new LexFloat(122.2, 1);
            lexs[1]=new LexOperand('+',2);
            lexs[2]=new LexFloat(10.2,3);
            lexs[3]=new LexOperand('-',4);
            lexs[4]=new LexFloat(40,5);
            lexs[5]=new LexOperand('*',6);
            lexs[6]=new LexFloat(3, 7);
            lexs[7]=new LexOperand('+',8);
            lexs[8]=new LexFloat(22,9);
            lexs[9]=new LexOperand('*',10);
            lexs[10]=new  LexFloat(11,9);
            lexs[11]=new LexOperand('*',10);
            lexs[12]=new  LexFloat(11,9);
            lexs[13]=new LexOperand('-',12);
            lexs[14]=new LexFloat(40,5);
            lexs[15]=new LexOperand('-',14);
            lexs[16]=new LexFloat(40,15);
           TreeLiterals tree =new TreeLiterals(lexs);
            tree.goRound();
            printTree(tree.getNode());
            // INPUT << 122.2+10.2-40.5*3+22*11*11-40-40
            // add :
            //    real110 dst1 ?
            //    real110 dst2 ?
            //    pop dst1
            //    pop dst2
            //    add dst1 , dst2
            //    push dst1
            /**-------------OUTPUT------------------------
             * 10.2    ->  push 10.2    {Stack: [ 10.2 ]}              | res =0
             * 40.0    ->  push 40.0    {Stack: [ 10.2 , 40.0 ]}       | res =0
             * 3.0     ->  push  3.0    {Stack: [ 10.2 , 40.0 , 3.0]}  | res =0
             * *       ->  mul          {Stack: [ 10.2,120 ]}          | res=120
             * -       ->  sub          {Stack: [  -109.8 ]}           | res=-109.8 
             * 22.0    ->  push 22.0    {Stack: [-109.8, 22.0 ]}       | res=-109.8
             * 11.0    ->  push 11.0    {Stack: [ -109.8, 22.0 , 11.0]}| res=-109.8
             * *       ->  mul          {Stack: [ -109.8, 242]}        | res=-109.8
             * 11.0    ->  push 11.0    {Stack: [-109.8, 242, 11.0  ]} | res=-109.8  
             * *       ->  mull         {Stack: [ -109.8 , 2662 ]}     | res=-109.8 
             * +       ->  add          {Stack: [ 2552.2   ]}          | res=2512.2 
             * 40.0    ->  push 40.0    {Stack: [ 2552.2 , 40.0]}      | res=2512.2 
             * -       ->  sub          {Stack: [ 2512.2   ]}          | res=2512.2 
             * 40.0    ->  push 40.0    {Stack: [ 2512.2 , 40  ]}      | res=2512.2 
             * -       ->  sub          {Stack: [ 2512.2   ]}          | res=2472.2 
             * 122.2   ->  push 122.2   {Stack: [ 2512.2 , 122.2 ]}    | res=2594.4
             * +       ->  add           {Stack:[ ]}                   | res=2594.4
             * ret 2594.4;
             * //output 2594.4
             */
    }
    
}
