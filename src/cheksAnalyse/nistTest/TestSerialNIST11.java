package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.Arrays;
import static org.apache.commons.math3.special.Gamma.regularizedGammaQ;

/**
 *
 * @author Michael Roussel rousselm4@gmail.com
 */
public class TestSerialNIST11  extends AbstractNistTest{

    public static final int BITS_NEEDED = 1000000; //TODO Set this value
    public static final String TABLE_NAME = "serial_NIST_11";
    
    public TestSerialNIST11(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_11;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        int MAX_SIZE_PATTERN_ONE = 65536;   //Pre computed value
        int MAX_SIZE_PATTERN_TWO = 32768;   //Pre computed value
        int MAX_SIZE_PATTERN_THR = 16384;   //Pre computed value
        int pattern_length = 16; // Must be less than 17 ([log(n)]-2 where log is in base 2 and n = bin_data.length
        int n = bits.length;
        // Add first m-1 bits to the end
        boolean[] bin_data2 = new boolean[n + pattern_length-1];
        System.arraycopy(bits, 0, bin_data2, 0, n);
        System.arraycopy(bits, 0, bin_data2, n, pattern_length-1);

        int[] vobs_one = new int[MAX_SIZE_PATTERN_ONE];
        for (int i = 0; i < vobs_one.length; i++) {
            vobs_one[i] = 0;
        }
        
        int[] vobs_two = new int[MAX_SIZE_PATTERN_TWO];
        for (int i = 0; i < vobs_two.length; i++) {
            vobs_two[i] = 0;
        }
        
        int[] vobs_thr = new int[MAX_SIZE_PATTERN_THR];
        for (int i = 0; i < vobs_thr.length; i++) {
            vobs_thr[i] = 0;
        }
        for (int i = 0; i < n; i++) {
            vobs_one[Integer.parseInt(boolArrayToString(Arrays.copyOfRange(bin_data2, i, i+pattern_length)), 2)] +=1;
            vobs_two[Integer.parseInt(boolArrayToString(Arrays.copyOfRange(bin_data2, i, i+pattern_length-1)), 2)] +=1;
            vobs_thr[Integer.parseInt(boolArrayToString(Arrays.copyOfRange(bin_data2, i, i+pattern_length-2)), 2)] +=1;
        }
        
        int[][] all_vobs = {vobs_one, vobs_two, vobs_thr};
        double[] sums = {0,0,0};
        for (int i = 0; i < sums.length; i++) {
            for (int j = 0; j < all_vobs[i].length; j++) {
                 sums[i] += Math.pow(all_vobs[i][j], 2);
            }
            sums[i] = (sums[i] * Math.pow(2, pattern_length-i) / n) - n;
        }
        
        double del1 = sums[0] - sums[1];
        double del2 = sums[0] - 2.0 * sums[1] + sums[2];
        
        double p_val_one = regularizedGammaQ(Math.pow(2, pattern_length - 1) / 2, del1 / 2.0);
        double p_val_two = regularizedGammaQ(Math.pow(2, pattern_length - 2) / 2, del2 / 2.0);
        this.pValue = Math.min(p_val_one, p_val_two);
    }

    public static String boolArrayToString(boolean[] array){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]?"1":"0");
        }
        return builder.toString();
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
