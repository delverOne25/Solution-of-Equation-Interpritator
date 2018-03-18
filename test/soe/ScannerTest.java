/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soe;

import java.util.Arrays;
import java.util.List;
import lexem.LexFloat;
import lexem.Lexem;
import lexem.LexOperand;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author danii
 */
@RunWith(Parameterized.class)
public class ScannerTest {
    
    @Parameterized.Parameters
    public static List<Object[]>dataForTest(){
        return Arrays.asList(new Object[][]{
            {"3+5*9-4",new Lexem[]{new LexFloat(3.0,0),new LexOperand('+', 1),new LexFloat(5.0,2),new LexOperand('*', 3)
                                ,new LexFloat(9.0,4),new LexOperand('-', 5),new LexFloat(4.0,6)}},
           {"19*3",new Lexem[]{new LexFloat(19.0,0),new LexOperand('*', 2),new LexFloat(3.0,3)}},
            {"11",new Lexem[]{new LexFloat(11.0,0)}},
            {"11 * 11+3 +3",new Lexem[]{ new LexFloat(11.0,0),new LexOperand('*', 3),new LexFloat(11.0,5),
                new LexOperand('+',7),new LexFloat(3.0,8),new LexOperand('+', 10),new LexFloat(3.0,11)}},
            {"-34- 323.4  -3", new Lexem[]{ new LexFloat(-34.0,0),new LexOperand('-', 3),
                new LexFloat(323.4,5),new LexOperand('-',12),new LexFloat(3.0,13)}},
        
        });
    }
    private final String strData;
    private final  Lexem[] expected;
    private final Scanner scan;
    public ScannerTest(String str, Lexem[] expected) {
        strData=str;
        this.expected=expected;
        scan=new Scanner(str);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        String stringPrinmer="3+5*9-4";
        String stringPrimer2="19*3";
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of readLex method, of class Scanner.
     */
    @Test
    public void testReadLex() throws Exception {
        System.out.println("readLex input str=\""+strData+"\"");
        Scanner instance = scan;
        Lexem[] expResult = expected;
        Lexem[] result = instance.readLex();
       
        assertTrue("No lexem parse ",equalsAssert(expected,result));
       
    }
   @Test
    public void lexBuildTest(){
        final Lexem actual = lexBuild("+",1);
        final Lexem expected=new LexOperand('+',1);
        assertEquals("lexBuild Operand False!!! ",actual.getVal(),expected.getVal());
    }
    private Lexem lexBuild(String val,int pos){
        if(val.matches("^[0-9\\.]+$")){
            return new lexem.LexFloat(Double.parseDouble(val),pos);
        } else if(val.length()==1)
            return new LexOperand(val.charAt(0),pos);
        return null;
    }
    private boolean equalsAssert(Lexem[] expected, Lexem[] actuals){
        if(expected.length!=actuals.length)
            return false;
        for(int i=0; i<expected.length;i++){
          
            if(expected[i].getClass()!= actuals[i].getClass() 
                    ||   expected[i].getPos()!=actuals[i].getPos() 
                    ||   expected[i].equals(actuals[i])
                    ||  expected[i].getType()!=actuals[i].getType())
                 return false;
        }
        return true;
        
    }
}
