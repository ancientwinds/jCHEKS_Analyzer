package cheksAnalyse.NIST;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.Saver;
import static org.apache.commons.math3.special.Gamma.regularizedGammaQ;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 * 
 * NIST Test 2.4: Longest Run in a Block
 */
public class TestLongestRunNIST4 extends AbstractNistTest{

    private int blockLength = 128;

    public static String TABLE_NAME = "LongestRun_NIST_4";
    private final double ratios[] = {0.1174, 0.2430, 0.2493, 0.1752, 0.1027, 0.1124};
    
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
        int[] runsLength = this.calculateBlocksLongestRun(bitsBlocks);
        int[] buckets = this.calculateBucketContent(runsLength);
        double x2Obs = this.calculateX2Obs(buckets);
        this.pValue = this.calculatePValue(x2Obs);
        
        this.passed = this.pValue > 0.01;
    }
    
    public int[] calculateBucketContent(int[] runsLength) {
        int[] buckets = new int[6];
        for(int i = 0; i < runsLength.length; i++) {
            switch(runsLength[i]) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    buckets[0]++;
                    break;
                case 5:
                    buckets[1]++;
                    break;
                case 6:
                    buckets[2]++;
                    break;
                case 7:
                    buckets[3]++;
                    break;
                case 8:
                    buckets[4]++;
                    break;
                default:
                    buckets[5]++;
                    break;                   
            }
        }
        
        return buckets;
    }
    
    public int[] calculateBlocksLongestRun(boolean[][] blocks) {
        int[] lengths = new int[blocks.length];
        for(int i = 0; i < blocks.length; i++) {
            lengths[i] = this.calculateLongestRun(blocks[i]);
        }
        
        return lengths;
    }
    
    public int calculateLongestRun(boolean[] block) {
        int runLength = 0;
        int longestRun = runLength;
        for(int i = 0; i < block.length; i++) {
            if(block[i]) {
                runLength++;                              
            } else {
                runLength = 0;
            }
            if(runLength > longestRun) {
                longestRun = runLength;
            }
        }        
        return longestRun;
    }
    
    public double calculateX2Obs(int[] buckets) {
        double X2Obs = 0.0;
        
        for(int i = 0; i < buckets.length; i++) {
            X2Obs += Math.pow((double)buckets[i] - (49 * ratios[i]), 2) / (49 * ratios[i]);
        }
        
        return X2Obs;
    }
    
    public double calculatePValue(double X2Obs) {
        return regularizedGammaQ(2.5, X2Obs/(double)2);
    }
        
    @Override
    public void saveResult(Saver saver) {
        System.out.println(this.pValue);
        saver.saveNistResults(this.getSystemId(), TABLE_NAME, pValue);
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
