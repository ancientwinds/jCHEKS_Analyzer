/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheksAnalyse.nistTest;

import Utils.Utils;
import cheksAnalyse.FakeChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas
 */
public class TestDiscreteFourierTransformNIST6Test {
    
    private final TestDiscreteFourierTransformNIST6 instance;
    
    public TestDiscreteFourierTransformNIST6Test() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        instance = new TestDiscreteFourierTransformNIST6(sys, 100);       
    }

    /**
     * Test of executeTest method, of class TestDiscreteFourierTransformNIST6.
     */
    @Test
    public void testExecuteTest() {
    }

    /**
     * Test of transformSequence method, of class TestDiscreteFourierTransformNIST6.
     */
    @Test
    public void testTransformSequence() {
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};
        
        double[] sequence = instance.transformSequence(bits);
        
        assertEquals(bits.length, sequence.length);
        
        assertEquals(-1, sequence[0], 0.1);
        assertEquals(1, sequence[1], 0.1);
        assertEquals(1, sequence[2], 0.1);
        assertEquals(-1, sequence[3], 0.1);
        assertEquals(-1, sequence[4], 0.1);
        assertEquals(1, sequence[5], 0.1);
        assertEquals(1, sequence[6], 0.1);
        assertEquals(-1, sequence[7], 0.1);
        assertEquals(1, sequence[8], 0.1);
        assertEquals(-1, sequence[9], 0.1);
    }

    @Test
    public void testCalculateN0() {
        assertEquals(47.5, instance.calculateN0(), 0.01);
    }
    
    //TODO Verify if it's correct. Depend on the implementation of Discrete Fourier Transform.
    @Test
    public void testCalculateN1() {        
        boolean bits[] = new boolean[100];

        String bitsString = "1100100100001111110110101010001000100001011010001100001000110100110001001100011001100010100010111000";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        double[] sequence = instance.transformSequence(bits);
        double[] x = Utils.calculateDiscreteFourierTransformation(sequence);        
        double[] m = instance.calculateM(x);
        double t = instance.calculateT();

        assertEquals(46, instance.calculateN1(m, t), 0.01);
    }
    
    @Test
    public void testCalculateD() {        
        
        assertEquals(-1.376494, instance.calculateD(47.5, 46), 0.000001);
    }
    
    @Test
    public void testCalculatePValue() {
        boolean bits[] = new boolean[128];

        String bitsString = "1100100100001111110110101010001000100001011010001100001000110100110001001100011001100010100010111000";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        double[] sequence = instance.transformSequence(bits);
        double[] x = Utils.calculateDiscreteFourierTransformation(sequence);        
        double[] m = instance.calculateM(x);
        double t = instance.calculateT();
        double n0 = instance.calculateN0();
        double n1 = instance.calculateN1(m, t);        
        double d = instance.calculateD(n0, n1);

        double pValue = instance.calculatePValue(d);
        
        assertEquals(0.029523, pValue, 0.000001);
    }
    
    @Test
    public void testCalculateT() {
        assertEquals(17.308183, instance.calculateT(), 0.00001);
    }
    
    
}
