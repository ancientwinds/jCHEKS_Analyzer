package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.Arrays;
import static org.apache.commons.math3.special.Gamma.regularizedGammaQ;

/**
 *
 * @author Michael Roussel rousselm4@gmail.com
 */
public class TestNonOverlappingTemplateMatchingNIST7 extends AbstractNistTest{

    public static final int BITS_NEEDED = 1000000; 
    public static final String TABLE_NAME = "non_overlapping_template_matching_NIST_7";
    
    public TestNonOverlappingTemplateMatchingNIST7(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_7;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        
        int sequenceLength = bits.length;
        boolean[] pattern = {false,false,false,false,false,false,false,false,true}; //Same as "000000001"
        int patternLength = pattern.length;
        int blockCount = 8;
        int[] patternCountsInBits = new int[blockCount];
        int blockSize = sequenceLength/blockCount;;
        boolean[][] blocks = new boolean[blockCount][blockSize];
        
        for(int i = 0; i < blockCount; i++){
            
            int blockStart = i * blockSize;
            int blockEnd = blockStart + blockSize;
            boolean[] block = blocks[i] = Arrays.copyOfRange(bits, blockStart, blockEnd);
            
            int j = 0;
            while (j < blockSize){
                boolean[] subBlock = Arrays.copyOfRange(block, j, j + patternLength);
                if (Arrays.equals(subBlock, pattern)){
                    patternCountsInBits[i] += 1;
                    j += patternLength;
                }
                else{
                    j += 1;
                }
            }
        }
        
        double mean = (blockSize - patternLength + 1) / squared(patternLength);
        int doublePatternLength = 2 * patternLength;
        double variance = blockSize * ((1.0 / squared(patternLength)) - ((doublePatternLength - 1) / (squared(doublePatternLength))));
        
        double chiSquared = 0;
        for(int i = 0; i < blockCount; i++){
            System.out.println(patternCountsInBits[i] - mean);
            chiSquared += Math.pow(patternCountsInBits[i] - mean, 2.0) / variance;
        }
        
        this.pValue = regularizedGammaQ(blockCount/2, chiSquared/2);
    }
    
    public static double squared(int value){
        return Math.pow(2, value);
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
