package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import static org.apache.commons.math3.special.Gamma.regularizedGammaQ;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestApproximateEntropyNIST12 extends AbstractNistTest{
    
    public static String TABLE_NAME = "Approximate_Entropy_NIST_12";
    public static final int BITS_NEEDED = 100000;
    private int blockLength = 10;
    private int bitsLenght;
    public TestApproximateEntropyNIST12(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
    }
    
    public TestApproximateEntropyNIST12(AbstractChaoticSystem chaoticSystem, int bitsNeeded, int blockLength) throws Exception {
        super(chaoticSystem, bitsNeeded);
        this.blockLength = blockLength;       
    }

    @Override
    public void executeTest(boolean[] bits) {
        bitsLenght = bits.length;
        double q1 = this.computeQ(bits, blockLength);
        double q2 = this.computeQ(bits, blockLength + 1);
        
        double obs = this.calculateObs(q1, q2);
        
        this.pValue = this.calculatePValue(obs);
        this.passed = this.pValue > 0.01;
    }
    
     private double computeQ(boolean bits[], int m) {
        double numOfBlocks = (double) bitsLenght;
        int powLen = (int) Math.pow(2, m + 1) - 1;
        int p[] = new int[powLen];

        for(int i = 1; i < powLen - 1; i++) {
            p[i] = 0;
        }
        for(int i = 0; i < numOfBlocks; i++) {
            int k = 1;
            for(int j = 0; j < m; j++){
                k <<= 1;
                if(bits[(i + j) % bitsLenght]) {
                    k++;
                }
            }
            p[k - 1]++;
        }

        double sum = 0.0;
        int index = (int) Math.pow(2, m) - 1;
        for(int i = 0; i < (int)Math.pow(2, m); i++) {
            if(p[index] > 0) {
                sum += (double)p[index] * Math.log(p[index] / numOfBlocks);
            }
            index++;
        }

        sum /= numOfBlocks;
        
        return sum;
    }
    
    private double calculateObs(double q1, double q2) {
        return 2.0 * bitsLenght * (Math.log(2) - (q1 - q2));
    }
    
    private double calculatePValue(double obs) {
        return regularizedGammaQ(Math.pow(2, this.blockLength - 1), obs / 2);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
