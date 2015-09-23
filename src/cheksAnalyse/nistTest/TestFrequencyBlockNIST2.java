package cheksAnalyse.nistTest;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import static org.apache.commons.math3.special.Gamma.regularizedGammaQ;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 * 
 * NIST Test 2.2: Frequency Block Test
 */

public class TestFrequencyBlockNIST2 extends AbstractNistTest{

    private int blockLength = 2000;
    public static String TABLE_NAME = "FrequencyBlock_NIST_2";
    public static final int BITS_NEEDED = 100000;
    
    public TestFrequencyBlockNIST2(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_2;
    }
    
    public TestFrequencyBlockNIST2(AbstractChaoticSystem chaoticSystem, int bitsNeeded, int blockLength) throws Exception {
        super(chaoticSystem, bitsNeeded);
        this.blockLength = blockLength;
        this.type = AnalyserType.NIST_2;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, this.blockLength);//this.partitionBits(bits);
        double[] proportions = this.calculateProportion(blocks);
        double[] ratios = this.calculateRatio(proportions);
        double xObs = this.calculateXobs(ratios);
        this.pValue = this.calculatePValue(xObs);

        this.passed = pValue > 0.01;
    }
    
    public double[] calculateProportion(boolean[][] blocks) {
        double proportions[] = new double[blocks.length];
        
        for(int i = 0; i < blocks.length; i++) {
            double ones = 0;
            
            for(int j = 0; j < blocks[i].length; j++) {
                if(blocks[i][j] == true) {
                    ones++;
                }
            }            
            proportions[i] = ones/blocks[i].length;
        }
        
        return proportions;
    }
    
    public double[] calculateRatio(double[] proportions) {
        double ratios[] = new double[proportions.length];
        
        for(int i = 0; i < proportions.length; i++) {
            ratios[i] = Math.pow((proportions[i] - (double)1/(double)2), 2);
        }
        
        return ratios;
    }
    
    public double calculateXobs(double[] ratios) {
        double xObs = 0;
        
        double totalRatios = 0;
        for(int i = 0; i < ratios.length; i++) {
            totalRatios += ratios[i];
        }
        
        xObs = 4 * this.blockLength * totalRatios;
        
        return xObs;
    }
    
    public double calculatePValue(double xObs) {
        int blockCount = this.bitsNeeded/this.blockLength;
       
        return regularizedGammaQ((double)blockCount/2, xObs/2);
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
