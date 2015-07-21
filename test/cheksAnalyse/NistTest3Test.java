package cheksAnalyse;

import cheksAnalyse.butterfly.NistTest3;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class NistTest3Test {
    
    @Test
    public void testCalculateP() {
        NistTest3 instance = new NistTest3(10);       
               
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};
        
        double p = instance.calculateP(bits);
        
        assertEquals(0.6, p, 0.1);
    }
    
    @Test
    public void testCalculateT() {
        NistTest3 instance = new NistTest3(10);
        
        assertEquals(0.632455532, instance.calculateT(), 0.0001);

    }
    
    @Test
    public void testShouldContinue_should_return_true() {
        NistTest3 instance = new NistTest3(10);       
               
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};
        
        double p = instance.calculateP(bits);

        assertTrue(instance.shouldContinue(p));
    }
    
    @Test
    public void testShouldContinue_should_return_false() {
        NistTest3 instance = new NistTest3(10);       

        assertFalse(instance.shouldContinue(2.7));
    }
    
    @Test
    public void testCalculateSi() {
        NistTest3 instance = new NistTest3(10);       
               
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};
        
        int[] result = instance.calculateSi(bits);
        
        assertEquals(9, result.length);
        
        assertEquals(1, result[0]);
        assertEquals(0, result[1]);
        assertEquals(1, result[2]);
        assertEquals(0, result[3]);
        assertEquals(1, result[4]);
        assertEquals(1, result[5]);
        assertEquals(1, result[6]);
        assertEquals(1, result[7]);
        assertEquals(0, result[8]);
    }
    
    @Test
    public void testCalculateVobs() {
        NistTest3 instance = new NistTest3(10);       
               
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};
        
        int[] Si = instance.calculateSi(bits);
        
        int vObs = instance.calculateVobs(Si);
        
        assertEquals(7, vObs);
    }
    
    @Test
    public void testCalculatePValue() {
        NistTest3 instance = new NistTest3(10);       
               
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};      
        int[] Si = instance.calculateSi(bits); 
        double p = instance.calculateP(bits);
        int vObs = instance.calculateVobs(Si);
        
        double pValue = instance.calculatePValue(vObs, p);
        
        assertEquals(0.1472, pValue, 0.0001);
    }
    
    @Test
    public void testExecuteShouldPass() {
        NistTest3 instance = new NistTest3(10);       
               
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};
        
        instance.executeTest(bits);
        
        assertTrue(instance.isPassed());
    }
    
}
