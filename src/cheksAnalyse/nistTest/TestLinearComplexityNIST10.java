package cheksAnalyse.nistTest;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import static org.apache.commons.math3.special.Gamma.regularizedGammaQ;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestLinearComplexityNIST10 extends AbstractNistTest{
    
    public static String TABLE_NAME = "Linear_Complexity_NIST_10";

    private int blockLength = 1000;
    
    private final double[] ratios = {0.01047, 0.03125, 0.12500, 0.50000, 0.25000, 0.06250, 0.020833};
    public static final int BITS_NEEDED = 1000000;
    public TestLinearComplexityNIST10(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
    }
    
    public TestLinearComplexityNIST10(AbstractChaoticSystem chaoticSystem, int bitsNeeded, int blockLength) throws Exception {
        super(chaoticSystem, bitsNeeded);
        this.blockLength = blockLength;
    }

    @Override
    public void executeTest(boolean[] bits) {
        
        boolean[][] blocks = Utils.partitionBitsInBlocks(bits, blockLength);
        int[] complexities = this.calculateLinearComplixtyForBlocks(blocks);
        double[] tableT = this.createTableT(complexities);
        int[] buckets = this.createBuckets(tableT);
        double vObs = this.calculateVobs(buckets);
        this.pValue = this.calculatePValue(vObs);

        this.passed = pValue > 0.01;
    }
    
    public int[] calculateLinearComplixtyForBlocks(boolean[][] blocks) {
        int[] complexities = new int[blocks.length];
        
        for(int i = 0; i < blocks.length; i++) {
            complexities[i] = Utils.calculateLinearComplexity(blocks[i]);
        }
        
        return complexities;
    }
    
    public double[] createTableT(int[] complexities) {
        double[] t = new double[complexities.length];
        double u = this.calculateU();
        for(int i = 0; i < complexities.length; i++) {
            t[i] = Math.pow(-1, this.blockLength) * (complexities[i] - u) + 2.0/9.0;
        }
        
        return t;
    }
    
    public int[] createBuckets(double[] t) {
        int[] buckets = new int[7];
        
        for(int i = 0; i < t.length; i++) {
            if(t[i] <= -2.5) {
                buckets[0] += 1;
            } else if (t[i] > -2.5 && t[i] <= -1.5) {
                buckets[1] += 1;
            } else if (t[i] > -1.5 && t[i] <= -0.5) {
                buckets[2] += 1;
            } else if (t[i] > -0.5 && t[i] <= 0.5) {
                buckets[3] += 1;
            } else if (t[i] > -0.5 && t[i] <= 1.5) {
                buckets[4] += 1;
            } else if (t[i] > 1.5 && t[i] <= 2.5) {
                buckets[5] += 1;
            } else if (t[i] > 2.5) {
                buckets[6] += 1;
            }
        }        
        return buckets;
    }
    
    public double calculateVobs(int[] buckets) {
        double vObs = 0;
        double N = this.bitsNeeded / this.blockLength;
        
        for(int i = 0; i < buckets.length; i++) {
            vObs += Math.pow(buckets[i] - N * this.ratios[i], 2) / (N * this.ratios[i]);
        }
        
        return vObs;
    }
    
    public double calculatePValue(double vObs) {
        return regularizedGammaQ(3, vObs/2);
    }
    
    public double calculateU() {
        return (this.blockLength/2) + (9 + Math.pow(-1, this.blockLength+1))/36 - (this.blockLength/3 + 2/9)/Math.pow(2, this.blockLength);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
    
    
}
