package cheksAnalyse.nistTest;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.AbstractSaver;
import static org.apache.commons.math3.special.Gamma.regularizedGammaQ;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestApproximateEntropyNIST12 extends AbstractNistTest{
    
    public static String TABLE_NAME = "Approximate_Entropy_NIST_12";

    private int blockLength = 2;
    
    public TestApproximateEntropyNIST12(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, 100000);
    }
    
    public TestApproximateEntropyNIST12(AbstractChaoticSystem chaoticSystem, int bitsNeeded, int blockLength) throws Exception {
        super(chaoticSystem, bitsNeeded);
        this.blockLength = blockLength;
    }

    @Override
    public void executeTest(boolean[] bits) {
        boolean[] augmented1 = this.augmentBits(bits, this.blockLength - 1);
        boolean[] augmented2 = this.augmentBits(bits, this.blockLength + 1);
        
        boolean[][] blocks1 = Utils.partitionBitsInBlocks(augmented1, blockLength);
        boolean[][] blocks2 = Utils.partitionBitsInBlocks(augmented2, blockLength + 1);
        
        int[] occurences1 = this.countOccurence(blocks1, blockLength);
        int[] occurences2 = this.countOccurence(blocks2, blockLength + 1);
        
        double[] c1 = this.computeC(occurences1);
        double[] c2 = this.computeC(occurences2);
        
        double q1 = this.computeQ(c1);
        double q2 = this.computeQ(c2);
        
        double obs = this.calculateObs(q1, q2);
        
        this.pValue = this.calculatePValue(obs);
        this.passed = this.pValue > 0.01;
       }
    
    public boolean[] augmentBits(boolean[] bits, int m) {
        boolean[] augmented = new boolean[bits.length + m];
        
        System.arraycopy(bits, 0, augmented, 0, bits.length);
        System.arraycopy(bits, 0, augmented, bits.length, m);   
        
        return augmented;
    }
    
    public boolean[][] createOverlappingBlocks(boolean[] bits, int m) {
        boolean[][] blocks = new boolean[this.bitsNeeded][m];
        
        for(int i = 0; i < blocks.length; i++) {
            System.arraycopy(bits, i, blocks[i], 0, m);
        }
        
        return blocks;
    }
    
    public int[] countOccurence(boolean[][] blocks, int m) {
        int[] occurence = new int[(int)Math.pow(2, m)];
        
        for(int i = 0; i < blocks.length; i++) {
            occurence[Utils.convertBooleanArrayToInt(blocks[i])]++;
        }
        
        return occurence;
    }
    
    public double[] computeC(int[] occurences) {
        double[] c = new double[occurences.length];
        
        for(int i = 0; i < c.length; i++) {
            c[i] = (double)occurences[i] / this.bitsNeeded;
        }
        
        return c;
    }
    
    public double computeQ(double[] c) {
        double result = 0;
        
        for(int i = 0; i < c.length; i++) {
            double log = Math.log(c[i]);
            if(Double.isFinite(log)) {
                result += (double)c[i] * log;
            }
        }
        
        return result;
    }
    
    public double calculateObs(double q1, double q2) {
        return 2 * this.bitsNeeded * (Math.log(2) - (q1 - q2));
    }
    
    public double calculatePValue(double obs) {
        return regularizedGammaQ(Math.pow(2, this.blockLength - 1), obs / 2);
    }

    @Override
    public void saveResult(AbstractSaver saver) {
        saver.saveNistResults(this.getSystemId(), TABLE_NAME, pValue);
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
