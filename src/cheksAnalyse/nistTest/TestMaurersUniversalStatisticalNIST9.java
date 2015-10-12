package cheksAnalyse.nistTest;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import static org.apache.commons.math3.special.Erf.erfc;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestMaurersUniversalStatisticalNIST9 extends AbstractNistTest {

    private int blockLength = 7;
    private int blockForInitialization = 1280;
    private int bitsLength;
    public static String TABLE_NAME = "Maurers_Universal_Statistical_NIST_9";
    
    public TestMaurersUniversalStatisticalNIST9(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, 1000000);
    }
    
    public TestMaurersUniversalStatisticalNIST9(AbstractChaoticSystem chaoticSystem, int bitsNeeded, int blockLength, int initBlock) throws Exception {
        super(chaoticSystem, bitsNeeded);
        this.blockLength = blockLength;
        this.blockForInitialization = initBlock;
    }

    @Override
    public void executeTest(boolean[] bits) {
        bitsLength = bits.length;
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, blockLength);
        int[] tableT = this.createTTable(blocks);
        double[] sums = this.calculateSumsAndUpdateTableT(blocks, tableT);
        double f = this.calculateF(sums);
        
        this.pValue = this.calculatePValue(f);
        this.passed = pValue > 0.01;
        
    }
    
    private double calculatePValue(double f) {
        int K = (int) (Math.floor(bitsLength / this.blockLength) - this.blockForInitialization);
        double c = 0.7 - 0.8 / (double) this.blockLength + (4.0 + 32.0 / (double) this.blockLength) * Math.pow(K, -3.0 / (double) this.blockLength) / 15;
        double sigma = c * Math.sqrt(3.125 / (double) K);
        return erfc(Math.abs((f - 6.1962507) / (Math.sqrt(2) * sigma)));
    }
    
    private double calculateF(double[] sums) {
        return sums[sums.length - 1] / (bitsLength/this.blockLength - this.blockForInitialization);
    }
    
    private int[] createTTable(boolean[][] blocks) {
        int[] tableT = new int[(int)Math.pow(2, blockLength)];
        
        for(int i = 0; i < this.blockForInitialization; i++) {
            tableT[Utils.convertBooleanArrayToInt(blocks[i])] = i + 1;
        }        
        return tableT;
    }
    
    private double[] calculateSumsAndUpdateTableT(boolean[][] blocks, int[] tableT) {
        double[] sum = new double[bitsLength/this.blockLength];
        
        for(int i = this.blockForInitialization; i < bitsLength/this.blockLength; i++) {
            sum[i] = sum[i - 1] + Utils.logBase2(i + 1 - tableT[Utils.convertBooleanArrayToInt(blocks[i])]);            
            tableT[Utils.convertBooleanArrayToInt(blocks[i])] = i + 1;
        }        
        return sum;
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
