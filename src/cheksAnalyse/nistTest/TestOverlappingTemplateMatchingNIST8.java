package cheksAnalyse.nistTest;

import Utils.Hypergeometric;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.Arrays;
import static org.apache.commons.math3.special.Gamma.regularizedGammaQ;

/**
 *
 * @author Michael Roussel rousselm4@gmail.com
 */
public class TestOverlappingTemplateMatchingNIST8 extends AbstractNistTest{

    
    public static final int BITS_NEEDED = 1000000; 
    public static final String TABLE_NAME = "overlapping_template_matching_NIST_8";
    
    public TestOverlappingTemplateMatchingNIST8(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_8;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        int blockSize = 1032; 
        int bitsLength = bits.length;
        boolean[] pattern = {true,true,true,true,true,true,true,true,true}; // Same as "111111111"
        int pattern_size = pattern.length;
        
        int blocksCount = (int)Math.floor(bitsLength / blockSize);
            
        double lambda = (double)((blockSize - pattern_size + 1) / Math.pow(2, pattern_size));
        double eta = lambda / 2.0;
        
        int PIKS_LENGTH = 6;
        double[] piks = new double[PIKS_LENGTH];
        double diff = 0;
        for(int i = 0; i< PIKS_LENGTH-1; i++){
            piks[i] = getProb(i, eta);
            diff += piks[i];
        }
        piks[PIKS_LENGTH-1] = 1.0 - diff;
        int[] patternCounts = {0,0,0,0,0,0};
        for(int i = 0; i<blocksCount; i++){
            int block_start = i * blockSize;
            
            int block_end = block_start + blockSize;
            
            boolean[] block_data = Arrays.copyOfRange(bits, block_start, block_end);
            
            int patternFound = 0;
            
            int j = 0;
            
            while (j < blockSize){
                boolean[] sub_block = Arrays.copyOfRange(block_data, j, j + pattern_size);
                
                if (Arrays.equals(sub_block, pattern)){
                    patternFound += 1;
                }
                j++;
            }
            if (patternFound <= 4){
                patternCounts[patternFound] += 1;
            }
            else{
                patternCounts[5] += 1;
            }
        }
        
        Double chi_squared = 0.0;
        for(int i = 0; i < patternCounts.length; i++){
            chi_squared += squared(patternCounts[i] - blocksCount * piks[i]) / (blocksCount * piks[i]);
        }
        this.pValue = regularizedGammaQ(5.0/2.0, chi_squared/2);
    }
    
    private double squared(double value){
        return value*value;
    }
    
    private double getProb(double u, double x){
        double out = 1.0 * Math.exp(-x);
        if (u != 0){
            out = 1.0 * x * Math.exp(2 * -x) * Math.pow(2, -u) * Hypergeometric.eval1f1(u + 1, 2, x);
        }
        return out;
    }
    

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
