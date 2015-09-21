package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import edu.missouristate.mote.statistics.Hypergeometric;
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
        
        int n = bits.length;
        boolean[] pattern = {true,true,true,true,true,true,true,true,true}; // Same as "111111111"
        int pattern_size = pattern.length;
        
        int blocksCount = (int)Math.floor(n / blockSize);
        double lambda = (double)(blockSize - pattern_size + 1 / squared(pattern_size));
        System.out.println("Lambda value: " +lambda);
        double eta = lambda / 2.0;
        /*  OK n = len(binin)
            OK bign = int(n / num)
            OK m = len(mat)
            OK lamda = 1.0 * (num - m + 1) / 2 ** m
            OK eta = 0.5 * lamda
            OK pi = [pr(i, eta) for i in xrange(numi)]
            pi.append(1 - reduce(su, pi))
            v = [0 for x in xrange(numi + 1)]
            blocks = stringpart(binin, num)
            blocklen = len(blocks[0])
            counts = [occurances(i,mat) for i in blocks]
            counts2 = [(numi if xx > numi else xx) for xx in counts]
            for i in counts2: v[i] = v[i] + 1
            chisqr = reduce(su, [(v[i]-bign*pi[i])** 2 / (bign*pi[i]) for i in xrange(numi + 1)])
            pval = spc.gammaincc(0.5*numi, 0.5*chisqr)
            return pval*/
        
        // piks = [a, b, c, d, (a+b+c+d)]  where a,b,c,d are found from getProb.
        int PIKS_LENGTH = 6;
        double[] piks = new double[PIKS_LENGTH];
        
        double diff = 0;
        for(int i = 0; i< PIKS_LENGTH-1; i++){
            piks[i] = getProb(i, eta);
            diff += piks[i];
            System.out.println("Pik " + i + " :" + piks[i]);
        }
        
        piks[PIKS_LENGTH-1] = 1.0 - diff;
        System.out.println("Piks: " + Arrays.toString(piks));
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
            System.out.println("patternFound: " + patternFound);
            if (patternFound <= 4){
                patternCounts[patternFound] += 1;
            }
            else{
                patternCounts[5] += 1;
            }
        }
        System.out.println("patternsFound: " + Arrays.toString(patternCounts));
        System.out.println(Arrays.toString(patternCounts));
        double chi_squared = 0;
        System.out.println(blocksCount);
        for(int i = 0; i < patternCounts.length; i++){
            System.out.println(i + "a: " + (blocksCount * piks[i]));
            System.out.println(i + "b: " + Math.pow(patternCounts[i] - blocksCount * piks[i], 2.0));
            chi_squared += Math.pow(patternCounts[i] - blocksCount * piks[i], 2.0) / (blocksCount * piks[i]);
        }
        System.out.println(chi_squared);
        System.out.println(regularizedGammaQ(5.0/2.0, chi_squared/2));
        this.pValue = regularizedGammaQ(5.0/2.0, chi_squared/2);
    }
    
    private double squared(double value){
        return Math.pow(2, value);
    }
    
    private double getProb(double u, double x){
        double out = 1.0 * Math.exp(-x);
        if (u != 0){
            out = 1.0 * x * Math.exp(2 * -x) * squared(-u) * Hypergeometric.eval1f1(u + 1, 2, x);
        }
        return out;
    }
    

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
