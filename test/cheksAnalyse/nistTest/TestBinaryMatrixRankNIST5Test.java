package cheksAnalyse.nistTest;

import Utils.Utils;
import cheksAnalyse.FakeChaoticSystem;
import cheksAnalyse.nistTest.TestBinaryMatrixRankNIST5;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestBinaryMatrixRankNIST5Test {
    
    private final TestBinaryMatrixRankNIST5 instance;
    
    public TestBinaryMatrixRankNIST5Test() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        instance = new TestBinaryMatrixRankNIST5(sys, 20, 3, 3);
    }

    /**
     * Test of executeTest method, of class TestBinaryMatrixRankNIST5.
     */
    @Test
    public void testExecuteTest() {
         boolean bits[] = {false, true, false, true, true, false, false, true, false, false, true, false, true, false, true, false, true, true, false, true};

         instance.executeTest(bits);
         
         assertTrue(instance.isPassed());
    }

    @Test
    public void testCalculateRank() {
        boolean bits[] = {false, true, false, true, true, false, false, true, false, false, true, false, true, false, true, false, true, true, false, true};
        
        boolean[][][] matrices = Utils.createMatrices(bits, 3, 3);
        
        boolean[][] result1 = Utils.prepareMatrix(matrices[0]);
        boolean[][] result2 = Utils.prepareMatrix(matrices[1]);
        
        assertEquals(2, instance.calculateRank(result1));
        assertEquals(3, instance.calculateRank(result2));        
    }
    
    @Test
    public void testCalculateRanks() {
        boolean bits[] = {false, true, false, true, true, false, false, true, false, false, true, false, true, false, true, false, true, true, false, true};
        
        boolean[][][] matrices = Utils.createMatrices(bits, 3, 3);        
        int[] ranks = instance.calculteRanks(matrices);
        
        assertEquals(2, ranks[0]);
        assertEquals(3, ranks[1]);        
    }
    
    @Test
    public void testCalculateXobs() {
        boolean bits[] = {false, true, false, true, true, false, false, true, false, false, true, false, true, false, true, false, true, true, false, true};
        
        boolean[][][] matrices = Utils.createMatrices(bits, 3, 3);
        int[] ranks = instance.calculteRanks(matrices);
        
        double xObs = instance.calculateXobs(ranks);
        
        assertEquals(0.596953, xObs, 0.000001);
    }
    
    @Test
    public void testCalculatePValue() {
        boolean bits[] = {false, true, false, true, true, false, false, true, false, false, true, false, true, false, true, false, true, true, false, true};
        
        boolean[][][] matrices = Utils.createMatrices(bits, 3, 3);
        int[] ranks = instance.calculteRanks(matrices);
        
        double xObs = instance.calculateXobs(ranks);
        double pValue = instance.calculatePValue(xObs);
        assertEquals(0.741948, pValue, 0.000001);
    }

    
    
}
