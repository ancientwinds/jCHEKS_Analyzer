package cheksAnalyse;

import cheksAnalyse.butterfly.NistTest2;
import java.util.BitSet;
import java.util.HashMap;
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
    public void testPartitionBits() {
        NistTest2 instance = new NistTest2(10, 3);       
               
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] result = instance.partitionBits(bits);
        
        assertEquals(3, result.length);
        
        for(int i = 0; i < result.length; i++) {
            assertEquals(3, result[i].length);
            boolean block[] = new boolean[3];
            System.arraycopy(bits, i * 3, block, 0, 3);
            for(int j = 0; j < block.length; j++) {
                assertEquals(result[i][j], block[j]);
            }
        }
    }
    
    @Test
    public void testCalculateProportion() {
        NistTest2 instance = new NistTest2(10, 3);       
               
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = instance.partitionBits(bits);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        assertEquals((double)2/(double)3, proportions[0], 0.00001);
        assertEquals((double)1/(double)3, proportions[1], 0.00001);
        assertEquals((double)2/(double)3, proportions[2], 0.00001);

    }
    
    @Test
    public void testCalculateRation() {
        NistTest2 instance = new NistTest2(10, 3);       
               
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = instance.partitionBits(bits);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        double[] ratios = instance.calculateRatio(proportions);
        
        assertEquals(0.028, ratios[0], 0.001);
        assertEquals(0.028, ratios[1], 0.001);
        assertEquals(0.028, ratios[2], 0.001);
    }
    
    @Test
    public void testCalculateXobs() {
        NistTest2 instance = new NistTest2(10, 3);       
               
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = instance.partitionBits(bits);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        double[] ratios = instance.calculateRatio(proportions);
        
        assertEquals(1, instance.calculateXobs(ratios), 0.0001);
    }
    
    @Test
    public void testCalculatePValue() {
        NistTest2 instance = new NistTest2(10, 3);       
               
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] blocks = instance.partitionBits(bits);
        
        double[] proportions = instance.calculateProportion(blocks);
        
        double[] ratios = instance.calculateRatio(proportions);
        double xObs = instance.calculateXobs(ratios);
        
        assertEquals(0.8013, instance.calculatePValue(xObs), 0.0001);
    }
    
    @Test
    public void testExecuteTestShouldPass() {
        NistTest2 instance = new NistTest2(10, 3);       
               
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};
        instance.executeTest(bits);
                
        assertTrue(instance.isPassed());
    }
    
}
