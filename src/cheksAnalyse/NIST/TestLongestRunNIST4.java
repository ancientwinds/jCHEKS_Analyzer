package cheksAnalyse.NIST;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.Saver;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 * 
 * NIST Test 2.4: Longest Run in a Block
 */
public class TestLongestRunNIST4 extends AbstractNistTest{

    private int blockLength = 128;

    public static String TABLE_NAME = "LongestRun_NIST_4";
    private double ratios[] = {0.1174, 0.2430, 0.2493, 0.1752, 0.1027, 0.1124};
    
    public TestLongestRunNIST4(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, 100000);
        this.bitsNeeded = 100000;
    }
    
    public TestLongestRunNIST4(AbstractChaoticSystem chaoticSystem, int bitsNeeded, int blockLength) throws Exception {
        super(chaoticSystem, bitsNeeded);
        this.blockLength = blockLength;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        
        boolean[][] bitsBlocks = Utils.partitionBitsInBlocks(bits, blockLength);
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int[] calculateBlocksLongestRun(boolean[][] blocks) {
        int[] lengths = new int[blocks.length];
        for(int i = 0; i < blocks.length; i++) {
            lengths[i] = this.calculateLongestRun(blocks[i]);
        }
        
        return lengths;
    }
    
    public int calculateLongestRun(boolean[] block) {
        int runLength = 1;
        int longestRun = runLength;
        System.out.println(block.length);
        for(int i = 0; i < block.length; i++) {
            if(block[i] == block[i + 1]) {
                runLength++;
            } else {
                if(runLength > longestRun) {
                    longestRun = runLength;
                }
            }
        }        
        return longestRun;
    }
    
    @Override
    public void saveResult(Saver saver) {
        saver.saveNistResults(this.getSystemId(), TABLE_NAME, pValue);
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
