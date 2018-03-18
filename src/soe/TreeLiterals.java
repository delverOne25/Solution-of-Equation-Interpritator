/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soe;
import ecxeptions.LexemException;
import ecxeptions.UnknowOperationException;
import lexem.*;

/**
 *Главный класс виртульной машины, создает дерево лексем, и запускает ппроцедуры интерпритации лексем
 * @author delverOne25
 */
public class TreeLiterals {
    private  short type;
    private  Lexem lex;
    private int posRead;
    private Node currentNode;
    private int coll;
    private Lexem[] lexems;
    StackSOE stack;
    /**
     * Конструктор. Инициализирует стек.
     * @param lexems 
     */
    public TreeLiterals(Lexem[] lexems){
         coll=lexems.length;
        
        this.lexems=lexems;
        addFirstRoot(new Node(lexems[0]));
        this.stack=new StackSOE(coll);
    }
    /**
     * Главный метод - инициализирует интерпретатор виртуальной машины и помещает комманды в стек.
     * @throws UnknowOperationException
     * @throws LexemException 
     */
   public void start() throws UnknowOperationException, LexemException{
       start(getRootNode(currentNode));
   }
   /**
    * Главный метод - инициализирует интерпретатор виртуальной машины и помещает комманды в стек.
    * <p>Все комманды находятся в пакете <tt>lib</tt> и должны наследовать {@link lib.Command}
    * @param n   Узел Лексема с которой начинается обход
    * @throws UnknowOperationException  в случае не известного операнда или комманды, которую не смогут прочитать
    * @throws LexemException            в случае добавления динамичесого обавления не известной комманды <b>не реализованноо</b>
    */
    public void start(Node n) throws UnknowOperationException, LexemException{
  
        if(n.getLChild()!=null && n.getLChild().visited==false){
            n=n.getLChild();         
        }
       else if(n.getRChild()!=null && n.getRChild().visited==false)
            n=n.getRChild();
       else{
             n.visited=true;  
              stack.command(n.lex);  
             Node temp=n.getHead();
             if(temp==null || temp.visited)
                 return;
             n=temp;
       }
       start(n);
    }

    public void goRound(){

        for(int i=0;i<coll;i++){
            Lexem lex=lexems[i];
            Node node = new Node(lex);
            if(lex instanceof LexFloat){
                if(currentNode.lChild==null){
                    node.setHead(currentNode);
                    currentNode.lChild=node;            
                }
                else if(currentNode.rChild==null){
                    node.setHead(currentNode);
                    currentNode.rChild=node; 
                }
            }else if(lex  instanceof LexOperand){
                    // Если это симовл * большего приоритета чем пребедущий то подняться вверх
                    if(currentNode.lex.getType()==2 ){
                        if( currentNode.rChild==null){
                            node.setHead(currentNode); 
                            node.setLChild(currentNode.getLChild());
                            currentNode.getLChild().setHead(node);
                            currentNode.setLChild(node);
                        }else{
                            node.setHead(currentNode.getHead());
                            node.setRChild(currentNode);
                            currentNode.getHead().setRChild(node);
                            currentNode.setHead(node);
                        }
                    }
                    else if(currentNode.lex.getType()==node.lex.getType()) {   // + -> +
                    
                        if(   currentNode.head!=null){
                                node.setHead(currentNode.getHead());
                                currentNode.getHead().setRChild(node);
                        }
                            node.setLChild(currentNode);
                            currentNode.setHead(node);
                      
                    }else if(currentNode.lex.getType()<node.lex.getType()){ // + -> * 
                            node.setLChild(currentNode.getRChild());
                            node.setHead(currentNode);
                            currentNode.getRChild().setHead(node);
                            currentNode.setRChild(node);
  
                            
                    }else {  //  * -> +
                        while(currentNode.lex.getType()>node.lex.getType() && currentNode.getHead()!=null){ 
                            currentNode=currentNode.head;
              
                        }
                         //   node.setHead(currentNode/*.getHead()*/);
                         //   node.setLChild(currentNode.getRChild()); 
                         //   currentNode.getRChild().setHead(node);
                         //   currentNode/*.getHead()*/.setRChild(node);
                    if(   currentNode.head!=null){
                                node.setHead(currentNode.getHead());
                                currentNode.getHead().setRChild(node);
                        }
                            node.setLChild(currentNode);
                            currentNode.setHead(node);  
                       
                    } 
                    currentNode=node;       
                } else if(lex instanceof LexBracket){
                    if(lex.getType()==2){ // Открывающая скобка
                        node.setHead(currentNode);
                        currentNode.rChild=node;
                        currentNode=node;
                    } else if(lex.getType()==3){ // закрывающая скобка
                        // подняться до скобки
                        while(currentNode.getLex().getType()!=2 && currentNode.getHead()!=null ){
                            
                            currentNode=currentNode.getHead();
                        }
                        currentNode.setRChild(node);
                        node.setHead(currentNode);
                        
                    }
                }         
        }
    }
    private Node getRootNode(Node node){
        if(node.getHead()!=null)
            return getRootNode(node.getHead());
        return node;
    }
    private void addFirstRoot(Node n){
        Node n1=new Node(new LexFloat(0,-1));
        Node n2=new Node(new LexOperand('+', -1));
        n1.head=n2;
        n2.lChild=n1;
        currentNode=n2;

    }
    public void addNode(Node n){
        
    }
    public void moveNode(){
        
    }
    public void removeNode(Node n){
        
    }
    public String getResult(){
        return stack.returnRes();
    }
    public Node getNode(){return currentNode;}
    public int getCount(){return coll;}
    
    
    
    public class Node{
        public boolean visited=false;
        private Lexem lex;
        private Node head;
        private Node lChild;
        private Node rChild;
        public Node(Lexem lex){
            this.lex=lex;
        }
        public void setHead(Node h){head=h;}
        public void setRChild(Node r){rChild=r;}
        public void setLChild(Node l){lChild=l;}
        public Lexem getLex(){return lex;}
        public Node getRChild(){return rChild;}
        public Node getLChild(){return lChild;}
        public Node getHead(){return head;}
    }
    


}
