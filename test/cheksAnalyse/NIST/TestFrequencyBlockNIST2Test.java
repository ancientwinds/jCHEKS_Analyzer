package cheksAnalyse.NIST;

import cheksAnalyse.nistTest.TestFrequencyBlockNIST2;
import Utils.Utils;
import cheksAnalyse.FakeChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestFrequencyBlockNIST2Test {
    
    
    private final TestFrequencyBlockNIST2 instance;
    
    public TestFrequencyBlockNIST2Test() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        instance = new TestFrequencyBlockNIST2(sys, 10, 3);       
    }

    @Test
    public void testCalculateProportion() throws Exception {
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 3);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        assertEquals((double)2/(double)3, proportions[0], 0.00001);
        assertEquals((double)1/(double)3, proportions[1], 0.00001);
        assertEquals((double)2/(double)3, proportions[2], 0.00001);

    }
    
    @Test
    public void testCalculateRation() throws Exception {
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 3);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        double[] ratios = instance.calculateRatio(proportions);
        
        assertEquals(0.028, ratios[0], 0.001);
        assertEquals(0.028, ratios[1], 0.001);
        assertEquals(0.028, ratios[2], 0.001);
    }
    
    @Test
    public void testCalculateXobs() throws Exception {
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 3);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        double[] ratios = instance.calculateRatio(proportions);
        
        assertEquals(1, instance.calculateXobs(ratios), 0.0001);
    }
    
    @Test
    public void testCalculatePValue() throws Exception {
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 3);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        double[] ratios = instance.calculateRatio(proportions);
        double xObs = instance.calculateXobs(ratios);
        
        assertEquals(0.8013, instance.calculatePValue(xObs), 0.0001);
    }
    
    @Test
    public void testExecuteTestShouldPass() throws Exception {
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};
        instance.executeTest(bits);
                
        assertTrue(instance.isPassed());
    }
    
}
