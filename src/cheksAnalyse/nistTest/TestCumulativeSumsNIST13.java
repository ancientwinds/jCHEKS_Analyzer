package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestCumulativeSumsNIST13 extends AbstractNistTest{

    public static final int BITS_NEEDED = 1000000;
    public static final String TABLE_NAME = "cumulative_sums_NIST_13";
    
    public TestCumulativeSumsNIST13(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_13;
    }
    
    @Override
    public void executeTest(boolean[] bin_data) {
        int n = bin_data.length;
        
        int[] counts = new int[n];
        for(int i = 0; i<n;i++){
            counts[i] = 0;
        }

        int ix = 0;
        
        for (boolean bool : bin_data) {
            int sub = 1;
            if(!bool){
                sub = -1;
            }
            if (ix>0){
                counts[ix] = counts[ix-1] + sub;
            } else{
                counts[ix] = sub;
            }
            ix++;
        }
        
        int abs_max = 0;
        for (int count : counts) {
            int absCount = Math.abs(count);
            abs_max = (abs_max < absCount) ? absCount : abs_max;
        }
        
        int start = (int)(Math.floor(0.25 * Math.floor(-n / abs_max) + 1));
        
        int end = (int)(Math.floor(0.25 * Math.floor(n / abs_max) - 1));
        
        NormalDistribution normalDistribution = new NormalDistribution();
        double[] terms_one = new double[end + 1 - start];
        System.out.println(terms_one.length);
        for (int i = start; i <= end; i++) {
            double sub = normalDistribution.cumulativeProbability((4 * i - 1) * abs_max / Math.sqrt(n));
            terms_one[i-start] = normalDistribution.cumulativeProbability((4 * i + 1) * abs_max / Math.sqrt(n)) - sub;
        }
        
        start = (int)(Math.floor(0.25 * Math.floor(-n / abs_max - 3)));
        
        end = (int)(Math.floor(0.25 * Math.floor(n / abs_max - 1)));
        
        double[] terms_two = new double[end + 1 - start];
        
        for (int i = start; i <= end; i++) {
            double sub = normalDistribution.cumulativeProbability((4 * i + 1) * abs_max / Math.sqrt(n));
            terms_two[i - start] = normalDistribution.cumulativeProbability(((4 * i + 3) * abs_max / Math.sqrt(n))) - sub;
        }
        
        double sum1 = 0;
        for (double value : terms_one) {
            sum1+=value;
        }
        
        double sum2 = 0;
        for (double value : terms_two) {
            sum2 += value;
        }
        double p_val = 1.0 - sum1;
        p_val += sum2;
        
        this.pValue = p_val;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
