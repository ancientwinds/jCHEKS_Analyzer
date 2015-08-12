package cheksAnalyse;

import Utils.Utils;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class UtilsTest {

    
    /**
     * Test of bytesToBooleanArray method, of class Utils.
     */
    @Test
    public void testBytesToBooleanArray() {
        byte[] bytes = new byte[]{8,8};
        boolean[] expResult = new boolean[]{false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false};
        boolean[] result = Utils.bytesToBooleanArray(bytes);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of byteToBooleanArray method, of class Utils.
     */
    @Test
    public void testByteToBooleanArray() {
        byte aByte = 8;
        boolean[] expResult = new boolean[]{false, false, false, false, true, false, false, false};
        boolean[] result = Utils.byteToBooleanArray(aByte);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of concatByteArrays method, of class Utils.
     */
    @Test
    public void testConcatByteArrays() {
        byte[] bytes1 = new byte[]{8,8};
        byte[] bytes2 = new byte[]{8,8};
        byte[] expResult = new byte[]{8,8,8,8};
        byte[] result = Utils.concatByteArrays(bytes1, bytes2);
        assertTrue(Arrays.equals(expResult, result));
    }
    
    @Test
    public void testPartitionBits() throws Exception {  
        boolean bits[] = {false, true, true, false, false, true, true, false, true, false};        
              
        boolean[][] result = Utils.partitionBitsInBlocks(bits, 3);
        
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
    public void testCreateMatrices() {
        boolean bits[] = {false, true, false, true, true, false, false, true, false, false, true, false, true, false, true, false, true, true, false, true};
        
        boolean[][][] matrices = Utils.createMatrices(bits, 3, 3);
        
        assertEquals(matrices.length, 2);
        
        assertFalse(matrices[0][0][0]);
        assertTrue(matrices[0][0][1]);
        assertFalse(matrices[0][0][2]);

        assertTrue(matrices[0][1][0]);
        assertTrue(matrices[0][1][1]);
        assertFalse(matrices[0][1][2]);

        assertFalse(matrices[0][2][0]);
        assertTrue(matrices[0][2][1]);
        assertFalse(matrices[0][2][2]);
        
        assertFalse(matrices[1][0][0]);
        assertTrue(matrices[1][0][1]);
        assertFalse(matrices[1][0][2]);

        assertTrue(matrices[1][1][0]);
        assertFalse(matrices[1][1][1]);
        assertTrue(matrices[1][1][2]);

        assertFalse(matrices[1][2][0]);
        assertTrue(matrices[1][2][1]);
        assertTrue(matrices[1][2][2]);        
    }
    
    @Test
    public void testForwardTransformation() {
        boolean bits[] = {false, true, false, true, true, false, false, true, false, false, true, false, true, false, true, false, true, true, false, true};
        
        boolean[][][] matrices = Utils.createMatrices(bits, 3, 3);
        
        boolean[][] result = Utils.doForwardTransformation(matrices[0]);
        
        assertTrue(result[0][0]);
        assertTrue(result[0][1]);
        assertFalse(result[0][2]);

        assertFalse(result[1][0]);
        assertTrue(result[1][1]);
        assertFalse(result[1][2]);

        assertFalse(result[2][0]);
        assertFalse(result[2][1]);
        assertFalse(result[2][2]);
    }
    
    
    @Test
    public void testBackwardTransformation() {
        boolean bits[] = {false, true, false, true, true, false, false, true, false, false, true, false, true, false, true, false, true, true, false, true};
        
        boolean[][][] matrices = Utils.createMatrices(bits, 3, 3);
        
        boolean[][] forward = Utils.doForwardTransformation(matrices[0]);
        boolean[][] result = Utils.doBackwardTransformation(matrices[0]);
        
        assertTrue(result[0][0]);
        assertFalse(result[0][1]);
        assertFalse(result[0][2]);

        assertFalse(result[1][0]);
        assertTrue(result[1][1]);
        assertFalse(result[1][2]);

        assertFalse(result[2][0]);
        assertFalse(result[2][1]);
        assertFalse(result[2][2]);
    }
    
    @Test
    public void testXoring() {
        boolean bits[] = {false, true, false};
        boolean bits2[] = {false, true, false};
        
        boolean result[] = Utils.xoring(bits, bits2);
        
        assertFalse(result[0]);
        assertFalse(result[1]);
        assertFalse(result[2]);
    }
    
    @Test
    public void testXoring2() {
        boolean bits[] = {false, true, false};
        boolean bits2[] = {true, false, true};
        
        boolean result[] = Utils.xoring(bits, bits2);
        
        assertTrue(result[0]);
        assertTrue(result[1]);
        assertTrue(result[2]);
    }
    
    @Test
    public void testXoring3() {
        boolean bits[] = {false, true, false};
        boolean bits2[] = {true, true, true};
        
        boolean result[] = Utils.xoring(bits, bits2);
        
        assertTrue(result[0]);
        assertFalse(result[1]);
        assertTrue(result[2]);
    }
    
    @Test
    public void testPrepareMatrix() {
        boolean bits[] = {false, true, false, true, true, false, false, true, false, false, true, false, true, false, true, false, true, true, false, true};
        
        boolean[][][] matrices = Utils.createMatrices(bits, 3, 3);
        
        boolean[][] result = Utils.prepareMatrix(matrices[0]);
        
        assertTrue(result[0][0]);
        assertFalse(result[0][1]);
        assertFalse(result[0][2]);

        assertFalse(result[1][0]);
        assertTrue(result[1][1]);
        assertFalse(result[1][2]);

        assertFalse(result[2][0]);
        assertFalse(result[2][1]);
        assertFalse(result[2][2]);
    }
    
        /**
     * Test of convertBooleanArrayToInt method, of class TestMaurersUniveralStatisticalNIST9.
     */
    @Test
    public void testConvertBooleanArrayToInt() {
        boolean bits[] = {false, true, false, true, true, false, false};
        boolean bits2[] = {true, true, true, true, true, true, true};

        int i = Utils.convertBooleanArrayToInt(bits);        
        assertEquals(44, i);
        
        i = Utils.convertBooleanArrayToInt(bits2);
        assertEquals(127, i);
    }
    
}
