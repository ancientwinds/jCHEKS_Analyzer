/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheksAnalyse.NIST;

import Utils.Utils;
import cheksAnalyse.FakeChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author etudiant
 */
public class NistTest4Test {
    
    public NistTest4Test() {
    }

    @org.testng.annotations.Test
    public void testCalculateLongestRun() throws Exception {        
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        TestLongestRunNIST4 instance = new TestLongestRunNIST4(sys, 128, 8);
        
        boolean bits[] = new boolean[128];

        String bitsString = "11001100000101010110110001001100111000000000001001001101010100010001001111010110100000001101011111001100111001101101100010110010";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 8);
        assertEquals(2, instance.calculateLongestRun(blocks[0]));
        assertEquals(2, instance.calculateLongestRun(blocks[1]));
        assertEquals(3, instance.calculateLongestRun(blocks[2]));
        assertEquals(2, instance.calculateLongestRun(blocks[3]));
        assertEquals(2, instance.calculateLongestRun(blocks[4]));
        assertEquals(1, instance.calculateLongestRun(blocks[5]));
        assertEquals(2, instance.calculateLongestRun(blocks[6]));
        assertEquals(1, instance.calculateLongestRun(blocks[7]));
        assertEquals(1, instance.calculateLongestRun(blocks[8]));
        assertEquals(2, instance.calculateLongestRun(blocks[9]));
    }

    @Test
    public void calculateBlocksLongestRun() throws Exception {
        /*ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        NistTest4 instance = new NistTest4(sys, 128, 8);
        
        boolean bits[] = new boolean[128];

        String bitsString = "11001100000101010110110001001100111000000000001001001101010100010001001111010110100000001101011111001100111001101101100010110010";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, 8);
        int[] lengths = instance.calculateBlocksLongestRun(blocks);
        
        assertEquals(2, lengths[0]);
        assertEquals(2, lengths[1]);
        assertEquals(3, lengths[2]);
        assertEquals(2, lengths[3]);
        assertEquals(2, lengths[4]);
        assertEquals(1, lengths[5]);
        assertEquals(2, lengths[6]);
        assertEquals(1, lengths[7]);
        assertEquals(1, lengths[8]);
        assertEquals(2, lengths[9]);*/
    }
    
}
