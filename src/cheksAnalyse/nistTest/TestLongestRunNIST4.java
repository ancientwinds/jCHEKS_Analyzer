package cheksAnalyse.nistTest;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
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
    private final double ratios[] = {0.1174035788, 0.242955959, 0.249363483, 0.17517706, 0.102701071, 0.112398847};
    public static final int BITS_NEEDED = 6272;
    public TestLongestRunNIST4(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.bitsNeeded = BITS_NEEDED;
        this.type = AnalyserType.NIST_4; 
    }
    
    public TestLongestRunNIST4(AbstractChaoticSystem chaoticSystem, int bitsNeeded, int blockLength) throws Exception {
        super(chaoticSystem, bitsNeeded);
        this.blockLength = blockLength;
        this.type = AnalyserType.NIST_4;
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
            //System.out.println("Length " + i + ": " + runsLength[i]);
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
            double ratioCalculated = (double)49 * ratios[i];
            X2Obs += Math.pow((double)buckets[i] - ratioCalculated, 2) / ratioCalculated;
        }
        
        return X2Obs;
    }
    
    public double calculatePValue(double X2Obs) {
        return regularizedGammaQ((double)5/(double)2, X2Obs/(double)2);
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
