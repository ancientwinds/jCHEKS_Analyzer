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
public class TestLongestRunNIST4Test {
    
    private final TestLongestRunNIST4 instance;
    
    public TestLongestRunNIST4Test() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        instance = new TestLongestRunNIST4(sys, 128, 8);
    }

    @Test
    public void testCalculateLongestRun() throws Exception {        
        boolean bits[] = new boolean[128];

        String bitsString = "11001100000101010110110001001100111000000000001001001101010100010001001111010110100000001101011111001100111001101101100010110010";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 8);
        assertEquals(2, instance.calculateLongestRun(blocks[0]));
        assertEquals(1, instance.calculateLongestRun(blocks[1]));
        assertEquals(2, instance.calculateLongestRun(blocks[2]));
        assertEquals(2, instance.calculateLongestRun(blocks[3]));
        assertEquals(3, instance.calculateLongestRun(blocks[4]));
        assertEquals(1, instance.calculateLongestRun(blocks[5]));
        assertEquals(2, instance.calculateLongestRun(blocks[6]));
        assertEquals(1, instance.calculateLongestRun(blocks[7]));
        assertEquals(2, instance.calculateLongestRun(blocks[8]));
        assertEquals(2, instance.calculateLongestRun(blocks[9]));        
        assertEquals(1, instance.calculateLongestRun(blocks[10]));
        assertEquals(3, instance.calculateLongestRun(blocks[11]));
        assertEquals(2, instance.calculateLongestRun(blocks[12]));
        assertEquals(3, instance.calculateLongestRun(blocks[13]));
        assertEquals(2, instance.calculateLongestRun(blocks[14]));
        assertEquals(2, instance.calculateLongestRun(blocks[15]));

    }

    @Test
    public void calculateBlocksLongestRun() throws Exception {
        boolean bits[] = new boolean[128];

        String bitsString = "11001100000101010110110001001100111000000000001001001101010100010001001111010110100000001101011111001100111001101101100010110010";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 8);
        int[] lengths = instance.calculateBlocksLongestRun(blocks);
        assertEquals(2, lengths[0]);
        assertEquals(1, lengths[1]);
        assertEquals(2, lengths[2]);
        assertEquals(2, lengths[3]);
        assertEquals(3, lengths[4]);
        assertEquals(1, lengths[5]);
        assertEquals(2, lengths[6]);
        assertEquals(1, lengths[7]);
        assertEquals(2, lengths[8]);
        assertEquals(2, lengths[9]);        
        assertEquals(1, lengths[10]);
        assertEquals(3, lengths[11]);
        assertEquals(2, lengths[12]);
        assertEquals(3, lengths[13]);
        assertEquals(2, lengths[14]);
        assertEquals(2, lengths[15]);
    }
    
    @Test
    public void testCalculateBucketContent() throws Exception {
        boolean bits[] = new boolean[128];

        String bitsString = "11001100000101010110110001001100111000000000001001001101010100010001001111010110100000001101011111001100111001101101100010110010";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 8);
        int[] lengths = instance.calculateBlocksLongestRun(blocks);
        int[] buckets = instance.calculateBucketContent(lengths);
        
        assertEquals(16, buckets[0]);
        assertEquals(0, buckets[1]);
        assertEquals(0, buckets[2]);
        assertEquals(0, buckets[3]);
        assertEquals(0, buckets[4]);
        assertEquals(0, buckets[5]);
    }
    
    @Test
    public void testCalculateX2Obs() throws Exception {
        boolean bits[] = new boolean[128];

        String bitsString = "11001100000101010110110001001100111000000000001001001101010100010001001111010110100000001101011111001100111001101101100010110010";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 8);
        int[] lengths = instance.calculateBlocksLongestRun(blocks);
        
        double X2Obs = instance.calculateX2Obs(instance.calculateBucketContent(lengths));
        
        assertEquals(61.5016, X2Obs, 0.0001);
    }
    
    @Test
    public void testCalculatePValue() throws Exception {
        boolean bits[] = new boolean[128];

        String bitsString = "11001100000101010110110001001100111000000000001001001101010100010001001111010110100000001101011111001100111001101101100010110010";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 8);
        int[] lengths = instance.calculateBlocksLongestRun(blocks);
        
        double X2Obs = instance.calculateX2Obs(instance.calculateBucketContent(lengths));
        double pValue = instance.calculatePValue(X2Obs);
        
        assertEquals(0.0000000000059, pValue, 0.0000000000001);
    }
    
    @Test
    public void testExecuteTest_Should_Fail() throws Exception {
        boolean bits[] = new boolean[128];

        String bitsString = "11001100000101010110110001001100111000000000001001001101010100010001001111010110100000001101011111001100111001101101100010110010";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        instance.executeTest(bits);
        
        assertFalse(instance.isPassed());
    } 
}
