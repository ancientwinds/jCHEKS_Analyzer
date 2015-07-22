package cheksAnalyse.NIST;

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
public class NistTest2Test {
    
    
    @Test
    public void testExecuteTest() {
    }

    @Test
    public void testCalculateProportion() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        NistTest2 instance = new NistTest2(sys, 10, 3);       
           
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 3);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        assertEquals((double)2/(double)3, proportions[0], 0.00001);
        assertEquals((double)1/(double)3, proportions[1], 0.00001);
        assertEquals((double)2/(double)3, proportions[2], 0.00001);

    }
    
    @Test
    public void testCalculateRation() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        NistTest2 instance = new NistTest2(sys, 10, 3);       
           
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
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        NistTest2 instance = new NistTest2(sys, 10, 3);       
           
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 3);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        double[] ratios = instance.calculateRatio(proportions);
        
        assertEquals(1, instance.calculateXobs(ratios), 0.0001);
    }
    
    @Test
    public void testCalculatePValue() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        NistTest2 instance = new NistTest2(sys, 10, 3);       
           
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 3);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        double[] ratios = instance.calculateRatio(proportions);
        double xObs = instance.calculateXobs(ratios);
        
        assertEquals(0.8013, instance.calculatePValue(xObs), 0.0001);
    }
    
    @Test
    public void testExecuteTestShouldPass() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        NistTest2 instance = new NistTest2(sys, 10, 3);       
           
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};
        instance.executeTest(bits);
                
        assertTrue(instance.isPassed());
    }
    
}
