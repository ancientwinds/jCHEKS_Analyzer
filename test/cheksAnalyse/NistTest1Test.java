/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheksAnalyse;

import java.util.BitSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author etudiant
 */
public class NistTest1Test {
    
    @Test
    public void testCalculateSn() {
        NistTest1 instance = new NistTest1(10);
        
        BitSet bits = BitSet.valueOf(new long[] { Long.parseLong("1011010101", 2) });
        assertEquals(2, instance.calculateSn(bits));          
    }
    
    @Test
    public void testCalculateSobs() {
        NistTest1 instance = new NistTest1(10);
        
        BitSet bits = BitSet.valueOf(new long[] { Long.parseLong("1011010101", 2) });
        int Sn = instance.calculateSn(bits);
        
        assertEquals(0.632455532, instance.calculateSobs(bits, Sn), 0.00001);
        
    }
    
    @Test
    public void testCalculatePValue() {
        NistTest1 instance = new NistTest1(10);
        
        BitSet bits = BitSet.valueOf(new long[] { Long.parseLong("1011010101", 2) });
        int Sn = instance.calculateSn(bits);
        double Sobs = instance.calculateSobs(bits, Sn);
        
        assertEquals(0.5271, instance.calculatePValue(Sobs, Sn), 0.0001);
    }
    
    @Test
    public void testExecuteTestShouldPass() {
        NistTest1 instance = new NistTest1(10);
        BitSet bits = BitSet.valueOf(new long[] { Long.parseLong("1011010101", 2) });
        
        instance.executeTest(bits);
        
        assertTrue(instance.isPassed());
    }    
}
