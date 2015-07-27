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

    /*@Test
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
    } */
    
    @Test
    public void test() throws Exception {
        
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        TestLongestRunNIST4 instance2 = new TestLongestRunNIST4(sys, 6272, 128);
        
        boolean bits[] = new boolean[6272];
        
        StringBuilder b = new StringBuilder();
        b.append("00001001001011010000010011000010101000100110000101001101010101100101101010110101000001011111011011000010001000000101011101100100101010100000000100011011000100000101010000111010011001010110110011000011010111100001000000010101000001010011100100101101101110100011001010000101100110001111011101010111010101110111110000010000001110011011011111101111110011010111111000100110010110101001000011111111001001000001010111101110010010000000010000101001001111111101110001011110001010100000000101001010111111011111110001011101011010110111010100001110111010111001111010010010110000110110010101010100101110011101101010111110001110110000111100010101110010110000101010010101010101111010110010011101001010110000000100101001101111101010011111100001101001100101100111010110111100000101001100000010111111110000110101011011010000000110101100001111100110100100010111011010000000111101000010011110100101011001101110100010010110001001000001001001010111010100100001100110011010000100000011011010000000000111001101110010101100000000101101010011110000000110110100100001110111100000010100001000101011010111110110001011110001110111101100011011000001001111110011111011110101101100101011010011100111011100110011010011001001000010110110011111111100100001110000000000011111000000001000001000011010100011011000000111111011100110111101111001011010111110001000101100101001011000000110101100000011001111011011000101110111101000100010101000101001110001010000110010100101011100100100010101110110000010001101100011010011011010011110100000000000101100100010110100011011010011110011101000000101100110110000000100010000011110110010000111001010100111111100111000101100010000100100100111101011001110011011101001000000100111100101110000000011101110010101100100001101001100001000110101010110101101111110001011100000010100110111010001100101100111100111111100100001111000010110010111010100100010000001111000110011110111010111010010111111110011011011110000000001000001001100001001010000101001000011000100110010101010101010100000101001001111000000010011011110100100000100010101100000101000011000110111100110111101000011101110000100001110001100010001000010110111001110010001010111100110110100000000110100101101100101101011010110010101010010011110010000010000111011100101110001001011110111000111111001100001111000010111101101010000101100001100100001011110011001000110101000010111101001010000010100110000010001000000100101001100110000100110101101001110100110000001111111001001100111010100011011101000100111011111110010111111001111100011101111101001010001000000000000110100101100011011100011110001010010011100010000001110010010100000101011010011001001011010011101000011111111000100110110100101011001110110101101011000111001000100101011100010001011110010111100100111111000100010101000000110101001010110001101101001001001111011101101111011011000111100000010001010110001101110011111010110011011000000001001110000001010101001010101111100000000110101010010110110001010101100000100000011000011011111011110101111100001111001000100010011100100111101000111000111101100000110110000000110001111001111011111100000101010110001000000000010001101111101111111011001100100011011001100101110111010010101101111100101010001110111000001100100001010011110100100010101000111001010100010110000000011001110101000101110010000000010000011101101010100101111111101100101000100000100100100010101010000000100000111110010001011111001100101000100001000110100000101011001110101110010111000001010111110010001000000001010110000111000100000001010110000010111111010111001000100101001111111011011110000110001100101100100101100011100100111011111011110111100011000100111001100010010101111100101100100000001000101010010101000001111110011101001110110110000001100111101010000100001110010000000110010110110011110111001110011001110111101101100000111110110000010110100110111110010100011110001111101010110011110101011010111001000101000001110001001110101100000111100011001111011110100101111000010001010001001000101101011111110111100110011000100101110000110011011000110000011111110111000110101100000111001001010111001000100001011010011101100010111101101011010111000001110111010001010010001001001010100111111010001110101001010101101110111101001010110111001010001011000001001011001010001111101011100101100110101001110110000100101100010011010100111110000010101001101001001110011000100011111101111011011010110001111010101010100111011111100100110111001110111011101111000111010100101101100111001101100010010001100001101110001101000111010111010110001111011110011011010101000000100110000110111111111111100110010001011011100110001100011100010101010000111001101001001010111011010110111100010000000000010011100100111010110100100111011000011111001110101100000111101011001011100110100110011100100000011101011101100000000000000110000010011010000010011010011011011110000110111001100000111001001100110001100110000101110101000000011100011001101011010001111111011100110000101100000000010110111111101000000000110100011001101011001010001011110100111100000001100000100110100100101000010100011001010011111100011101101110010000010010010011100011011001010101001100110001011001110100101000110001010010001000000010100101100110010010000011110011000111111001001111001001110001011011100111001101100100001010101011001100001111011011100100000001100011111011100001000100000100101010111001010001011000011011000011100001001001111110011011110101001110011011001000110100011111111000001011111100101100100111101001101101011001010100110111011001100000100100010110101011001010000100000110110111001101010110011010011001101011000101111000101101110001010101101010111010100101100001001101111010010110110110001100110010010110000100100001111101100111101100101100001010010010000011000000100100111011011110001101010111100010110101110001001101011001111000110010011101111000110101100100110000110011000111100010111101011110010111111100110010101111110001110000110110100111000011100110100001011000010101100011000111001001110100001100110011010001000111111110000011011001001100011011001011111111011011001110011100100110000011010011100100000011011110100100100001011111011100100100101011000101101011000110011110101100010100110100011010111111011111011001010001111011100001100110110111011010011111010011011111001100110111011001011000011100100011011110101001100110111100110001011011111101001000010001000011011111000101100111101111110000110");
        String bitsString = b.toString();
        
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 128);
        int[] lengths = instance2.calculateBlocksLongestRun(blocks);
        
        double ratios[] = {0.1174, 0.2430, 0.2493, 0.1752, 0.1027, 0.1124};

        
        double X2Obs = instance2.calculateX2Obs(instance2.calculateBucketContent(lengths));
        double pValue = instance2.calculatePValue(X2Obs);        
        //assertFalse(instance.isPassed());
    }   
    
    
}
