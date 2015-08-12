package cheksAnalyse.nistTest;

import cheksAnalyse.FakeChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestApproximateEntropyNIST12Test {    
    
    private final TestApproximateEntropyNIST12 instance;
    
    public TestApproximateEntropyNIST12Test() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        instance = new TestApproximateEntropyNIST12(sys, 10, 3);       
    }
    /**
     * Test of executeTest method, of class TestApproximateEntropyNIST12.
     */
    @Test
    public void testExecuteTest() {
        boolean bits[] = new boolean[10];

        String bitsString = "0100110101";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        instance.executeTest(bits);
        
        assertTrue(instance.isPassed());
    }

    /**
     * Test of augmentBits method, of class TestApproximateEntropyNIST12.
     */
    @Test
    public void testAugmentBits() {
        boolean bits[] = new boolean[10];

        String bitsString = "0100110101";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[] augemented = instance.augmentBits(bits, 2);
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < augemented.length; i++) {
            if(augemented[i]) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }
        assertTrue(sb.toString().equals("010011010101"));
    }
    
    @Test
    public void testCreateOverlappingBlocks() {
        boolean bits[] = new boolean[10];

        String bitsString = "0100110101";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[] augemented = instance.augmentBits(bits, 2);
        
        boolean[][] blocks = instance.createOverlappingBlocks(augemented, 3);
        
        assertEquals(blocks.length, bits.length);
        
        boolean[] array1 = {false, true, false};
        assertBooleanArray(array1, blocks[0]);
        boolean[] array2 = {true, false, false};
        assertBooleanArray(array2, blocks[1]);
        boolean[] array3 = {false, false, true};
        assertBooleanArray(array3, blocks[2]);
        boolean[] array4 = {false, true, true};
        assertBooleanArray(array4, blocks[3]);
        boolean[] array5 = {true, true, false};
        assertBooleanArray(array5, blocks[4]);
        boolean[] array6 = {true, false, true};
        assertBooleanArray(array6, blocks[5]);
        boolean[] array7 = {false, true, false};
        assertBooleanArray(array7, blocks[6]);
        boolean[] array8 = {true, false, true};
        assertBooleanArray(array8, blocks[7]);
        boolean[] array9 = {false, true, false};
        assertBooleanArray(array9, blocks[8]);
        boolean[] array10 = {true, false, true};
        assertBooleanArray(array10, blocks[9]);
        
    }
    
    @Test
    public void testCountOccurence() {
        boolean bits[] = new boolean[10];

        String bitsString = "0100110101";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[] augemented = instance.augmentBits(bits, 2);
        
        boolean[][] blocks = instance.createOverlappingBlocks(augemented, 3);
        
        int[] occurences = instance.countOccurence(blocks, 3);
        
        assertEquals(8, occurences.length);
        
        assertEquals(0, occurences[0]);
        assertEquals(1, occurences[1]);
        assertEquals(3, occurences[2]);
        assertEquals(1, occurences[3]);
        assertEquals(1, occurences[4]);
        assertEquals(3, occurences[5]);
        assertEquals(1, occurences[6]);
        assertEquals(0, occurences[7]);
    }
    
    @Test
    public void testComputeC() {
        boolean bits[] = new boolean[10];

        String bitsString = "0100110101";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[] augemented = instance.augmentBits(bits, 2);
        
        boolean[][] blocks = instance.createOverlappingBlocks(augemented, 3);
        
        int[] occurences = instance.countOccurence(blocks, 3);
        double[] c = instance.computeC(occurences);
        
        assertEquals(8, c.length);
        
        assertEquals(0.0, c[0], 0.1);
        assertEquals(0.1, c[1], 0.1);
        assertEquals(0.3, c[2], 0.1);
        assertEquals(0.1, c[3], 0.1);
        assertEquals(0.1, c[4], 0.1);
        assertEquals(0.3, c[5], 0.1);
        assertEquals(0.1, c[6], 0.1);
        assertEquals(0.0, c[7], 0.1);
    }
    
    @Test
    public void testComputeQ() {
        boolean bits[] = new boolean[10];

        String bitsString = "0100110101";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[] augemented = instance.augmentBits(bits, 2);
        
        boolean[][] blocks = instance.createOverlappingBlocks(augemented, 3);
        
        int[] occurences = instance.countOccurence(blocks, 3);
        double[] c = instance.computeC(occurences);
        
        double q = instance.computeQ(c);
        
        assertEquals(-1.64341772, q, 0.00000001);        
    }
    
    @Test
    public void testComputeQ2() {
        boolean bits[] = new boolean[10];

        String bitsString = "0100110101";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[] augemented = instance.augmentBits(bits, 3);
        
        boolean[][] blocks = instance.createOverlappingBlocks(augemented, 4);
        
        int[] occurences = instance.countOccurence(blocks, 4);
        double[] c = instance.computeC(occurences);
        
        double q = instance.computeQ(c);
        
        assertEquals(-1.83437197, q, 0.00000001);        
    }
    
    @Test
    public void testCalculateObs() {
        boolean bits[] = new boolean[10];

        String bitsString = "0100110101";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[] augemented1 = instance.augmentBits(bits, 2);
        boolean[] augemented2 = instance.augmentBits(bits, 4);
        
        boolean[][] blocks1 = instance.createOverlappingBlocks(augemented1, 3);
        boolean[][] blocks2 = instance.createOverlappingBlocks(augemented2, 4);
        
        int[] occurences1 = instance.countOccurence(blocks1, 3);
        int[] occurences2 = instance.countOccurence(blocks2, 4);
        
        double[] c1 = instance.computeC(occurences1);
        double[] c2 = instance.computeC(occurences2);
        
        double q1 = instance.computeQ(c1);
        double q2 = instance.computeQ(c2);
        
        double obs = instance.calculateObs(q1, q2);
        
        assertEquals(10.043858, obs, 0.000001);        
    }
    
    @Test
    public void testCalculatePValue() {
        boolean bits[] = new boolean[10];

        String bitsString = "0100110101";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[] augemented1 = instance.augmentBits(bits, 2);
        boolean[] augemented2 = instance.augmentBits(bits, 4);
        
        boolean[][] blocks1 = instance.createOverlappingBlocks(augemented1, 3);
        boolean[][] blocks2 = instance.createOverlappingBlocks(augemented2, 4);
        
        int[] occurences1 = instance.countOccurence(blocks1, 3);
        int[] occurences2 = instance.countOccurence(blocks2, 4);
        
        double[] c1 = instance.computeC(occurences1);
        double[] c2 = instance.computeC(occurences2);
        
        double q1 = instance.computeQ(c1);
        double q2 = instance.computeQ(c2);
        
        double obs = instance.calculateObs(q1, q2);
        
        double pValue = instance.calculatePValue(obs);
        
        assertEquals(0.261961, pValue, 0.000001); 
    }
    
    public void assertBooleanArray(boolean[] array1, boolean[] array2) {
        assertEquals(array1.length, array2.length);
        
        for(int i = 0; i < array1.length; i++) {
            assertEquals(array1[i], array2[i]);
        }
    }
    
    
}
