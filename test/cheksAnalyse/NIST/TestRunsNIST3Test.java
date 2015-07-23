package cheksAnalyse.NIST;

import cheksAnalyse.FakeChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestRunsNIST3Test {
    
    private final TestRunsNIST3 instance;
    
    public TestRunsNIST3Test() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        instance = new TestRunsNIST3(sys, 10);
    }
    
    @Test
    public void testCalculateP() throws Exception {
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};
        
        double p = instance.calculateP(bits);
        
        assertEquals(0.6, p, 0.1);
    }
    
    @Test
    public void testCalculateT() throws Exception {
        assertEquals(0.632455532, instance.calculateT(), 0.0001);

    }
    
    @Test
    public void testShouldContinue_should_return_true() throws Exception {
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};
        
        double p = instance.calculateP(bits);

        assertTrue(instance.shouldContinue(p));
    }
    
    @Test
    public void testShouldContinue_should_return_false() throws Exception {
        assertFalse(instance.shouldContinue(2.7));
    }
    
    @Test
    public void testCalculateSi() throws Exception {
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
    public void testCalculateVobs() throws Exception {
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};
        
        int[] Si = instance.calculateSi(bits);
        
        int vObs = instance.calculateVobs(Si);
        
        assertEquals(7, vObs);
    }
    
    @Test
    public void testCalculatePValue() throws Exception {
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};      
        int[] Si = instance.calculateSi(bits); 
        double p = instance.calculateP(bits);
        int vObs = instance.calculateVobs(Si);
        
        double pValue = instance.calculatePValue(vObs, p);
        
        assertEquals(0.1472, pValue, 0.0001);
    }
    
    @Test
    public void testExecuteShouldPass() throws Exception {
        boolean bits[] = {true, false, false, true, true, false, true, false, true, true};
        
        instance.executeTest(bits);
        
        assertTrue(instance.isPassed());
    }
    
    @Test
    public void test() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        TestRunsNIST3 instance2 = new TestRunsNIST3(sys, 128);
        
        boolean bits[] = new boolean[100];

        String bitsString = "1100100100001111110110101010001000100001011010001100001000110100110001001100011001100010100010111000";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        instance2.executeTest(bits);

        assertTrue(instance2.isPassed());
    }
    
}
