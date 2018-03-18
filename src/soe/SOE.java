/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soe;

import ecxeptions.LexemException;
import ecxeptions.UnknowOperationException;
import java.io.*;
import java.util.Scanner;
import lexem.Lexem;
/**
 *Solution of Equation
 * @author danii
 */
public class SOE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NumberFormatException {
        // TODO code application logic here
        args=new String[]{"3*6+4/4*2+5*1"};
        StringBuilder primer=new StringBuilder(36);
        String fileIn=null;
        String fileOut=null;
        int accurate=10000;;
        
        if(args.length>0){
            for(int i=0;i<args.length;i++){
                String key=args[i];
                switch(key){
                    case "-v":
                        String t=args[++i];
                            do{
                                primer.append(t);
                                t=args[++i];
                                
                            }while(!t.matches(("-in|-out|-v|-ac")));
                            i--;
                            break;
                    case "-in" :
                        fileIn=args[++i];
                        break;
                    case "-out":
                        fileOut=args[++i];
                        break;
                    case "-ac" :
                        accurate=Integer.parseInt(args[++i]);
                    default :
                        primer.append(key);
                        break;
                }
                    
                }
            
                
        String text=primer.toString();
        if( fileIn!=null){
            try(BufferedReader reader =new BufferedReader(new InputStreamReader(new FileInputStream(fileIn)))){
                text = reader.readLine();
                reader.close();
            }catch(FileNotFoundException ex){
                System.err.println("Не удалось открыть файл для записи вы можите, вести пример самостоятельно");
                text = new Scanner(System.in).nextLine();
            }catch(IOException ex){
                throw new NumberFormatException("Не удалось прочитать пример с файла");
            }
            /**
             * 1. Составить из примера двоичное дерево опреаций.Занести в него лексемы
             * 2. Пройтись по деру с самого низа, читая лексему и читая в соседнем сестринском узле, а вслучае
             *      если этот узел имеет дочернии то он так же рекрусивно выводит результат к узлу, и тот проводит операцию
             *          и так далее
             * 3. В стек функций начинают падать элементы(цифры, и опрарнды), выполнять их по таблице
             * 4. Вывеси результат вернуть или распечаатать
             */
        
            
        }
        
         /*---------------------------Main------------------------------------*/
            soe.Scanner scan =new soe.Scanner(text); // инициализация сканера
            Lexem[] lex;
            try{
                lex = scan.readLex(); // парсим строку в лексемы
            }catch(LexemException ex){
                System.err.println(ex.getMessage());
                return;
            }
            TreeLiterals tree=new TreeLiterals(lex); // инициализируем дерево
            
            tree.goRound();          // запускаем обход по лексемам, и строим структуру дерева
            
            try{//tree.printTree(tree.getNode());
                tree.start();        // запускаем Интерпретацию лексем в дереве в выполнение комманд машины
            }catch(UnknowOperationException | LexemException ex){
                System.err.println(ex.getMessage());
            } 
            String res=tree.getResult();  // получаем последний эллемент встеке, это наш результ
            
            /*------------------------------------------------------------------*/
            // Выводим результат
            double resDouble = 0;
            boolean error=false;
            try{
                resDouble=Double.parseDouble(res);
            }catch(NumberFormatException ex){
                error=true;
            }
            
            if(fileOut!=null){
                try(PrintWriter out =new PrintWriter(new FileOutputStream(fileOut), true)){
                    if(!error)
                        out.printf("%."+((accurate+"").length()-1)+"f\n",resDouble);
                    else
                        out.println(res);
                }catch(IOException ex){
                    System.err.println("Ошибка записи результата в файл");
                    return;
                }}
            else
           
                if(!error)
                    System.out.printf("result %."+((accurate+"").length()-1)+"f\n",resDouble);
                else 
                    System.err.println(res);
    }       
    
}
}
