package cheksAnalyse.nistTest;

import Utils.Utils;
import cheksAnalyse.FakeChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestMaurersUniveralStatisticalNIST9Test {
    
    private final TestMaurersUniveralStatisticalNIST9 instance;
    
    public TestMaurersUniveralStatisticalNIST9Test() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        instance = new TestMaurersUniveralStatisticalNIST9(sys, 20, 2, 4);
    }

    /**
     * Test of executeTest method, of class TestMaurersUniveralStatisticalNIST9.
     */
    @Test
    public void testExecuteTest() {
        boolean bits[] = {false, true, false, true, true, false, true, false, false, true, true, true, false, true, false, true, false, true, true, true};

        instance.executeTest(bits);
        
        assertTrue(instance.isPassed());
    }

    /**
     * Test of createTTable method, of class TestMaurersUniveralStatisticalNIST9.
     */
    @Test
    public void testCreateTTable() {
        boolean bits[] = {false, true, false, true, true, false, true, false, false, true, true, true, false, true, false, true, false, true, true, true};
                
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 2);
              
        int[] tableT = instance.createTTable(blocks);
        
        assertEquals(4, tableT.length);

        assertEquals(0, tableT[0]);
        assertEquals(2, tableT[1]);
        assertEquals(4, tableT[2]);
        assertEquals(0, tableT[3]); 
    }
    
    @Test
    public void testCalculateSumsAndUpdateTableT() {
        boolean bits[] = {false, true, false, true, true, false, true, false, false, true, true, true, false, true, false, true, false, true, true, true};
                
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 2);
              
        int[] tableT = instance.createTTable(blocks);
        
        double[] sums = instance.calculateSumsAndUpdateTableT(blocks, tableT);
        
        assertEquals(1.584962501, sums[4], 0.000000001);
        assertEquals(4.169925002, sums[5], 0.000000001);
        assertEquals(5.169925002, sums[6], 0.000000001);
        assertEquals(5.169925002, sums[7], 0.000000001);
        assertEquals(5.169925002, sums[8], 0.000000001);
        assertEquals(7.169925002, sums[9], 0.000000001);
        
        assertEquals(0, tableT[0]);
        assertEquals(9, tableT[1]);
        assertEquals(4, tableT[2]);
        assertEquals(10, tableT[3]); 
    }
    
    @Test
    public void testCalculateF() {
        boolean bits[] = {false, true, false, true, true, false, true, false, false, true, true, true, false, true, false, true, false, true, true, true};
                
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 2);
              
        int[] tableT = instance.createTTable(blocks);
        
        double[] sums = instance.calculateSumsAndUpdateTableT(blocks, tableT);
        double f = instance.calculateF(sums);
        
        assertEquals(1.1949875, f, 0.000000001);
    }   
    
    //TODO It's ok if it fail because of some hardcoded variable.
    @Test
    public void testCalculatePValue() {
        boolean bits[] = {false, true, false, true, true, false, true, false, false, true, true, true, false, true, false, true, false, true, true, true};
                
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 2);              
        int[] tableT = instance.createTTable(blocks);        
        double[] sums = instance.calculateSumsAndUpdateTableT(blocks, tableT);
        double f = instance.calculateF(sums);        
        
        double pValue = instance.calculatePValue(f);
        
        assertEquals(0.109509, pValue, 0.000001);
    }
    
}
