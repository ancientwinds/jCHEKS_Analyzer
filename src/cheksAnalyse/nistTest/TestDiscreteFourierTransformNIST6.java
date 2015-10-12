package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.Arrays;
import static org.apache.commons.math3.special.Erf.erfc;
import org.jtransforms.fft.DoubleFFT_1D;
/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestDiscreteFourierTransformNIST6 extends AbstractNistTest {
    
    public static String TABLE_NAME = "Discrete_Fourier_Transform_NIST_6";
    public static final int BITS_NEEDED = 100000;
    private int bitsLength;
    public TestDiscreteFourierTransformNIST6(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
    }
    
    public TestDiscreteFourierTransformNIST6(AbstractChaoticSystem chaoticSystem, int bitsNeeded) throws Exception {
        super(chaoticSystem, bitsNeeded);       
    }

    @Override
    public void executeTest(boolean[] bits) {
        bitsLength = bits.length;
        double[] sequence = this.transformSequence(bits);
        DoubleFFT_1D dfft = new DoubleFFT_1D(bitsLength);
        double[] doubledBits = new double[bitsLength*2];
        System.arraycopy(sequence, 0, doubledBits, 0, bitsLength);
        dfft.realForwardFull(doubledBits);
        double[] result = new double[bitsLength/2];
        System.arraycopy(doubledBits, bitsLength, result, 0, bitsLength/2);
        
        double[] m = this.calculateM(doubledBits);
        double t = this.calculateT();
        double n0 = this.calculateN0();
        double n1 = this.calculateN1(m, t);
        double d = this.calculateD(n0, n1);
        this.pValue = this.calculatePValue(d);
        this.passed = this.pValue > 0.01; 
    }
    
    private double[] calculateM(double[] x) {
        
        double[] m = new double[bitsLength/2];
        m[0] = Math.abs(x[0]);

        for(int i = 0; i < bitsLength / 2; i++) {
            if(2 * i + 2 >= bitsLength) {
                m[i] = Math.abs(x[2 * i]);
            } else {
                m[i] = Math.sqrt(Math.pow(x[2 * i ], 2) + Math.pow(x[2 * i + 1], 2));
            }
        }
        return m;
    }
    
    private double calculateT() {
        return Math.sqrt(2.995732274 * bitsLength);
    }
    
    private double[] transformSequence(boolean[] bits) {
        double[] sequence = new double[bits.length];
        
        for(int i = 0; i < bits.length; i++) {
            if(bits[i]) {
                sequence[i] = 1;
            } else {
                sequence[i] = -1;
            }
        }
        
        return sequence;
    }
    
    private double calculateN0() {
        return 0.95 * bitsLength / 2;
    }
    
    private double calculateN1(double[] m, double t) {
        double N1 = 0;
        
        for(int i = 0; i < m.length; i++) {
            if(m[i] < t) {
                N1++;
            }
        }
        
        return N1;
    }
    
    private double calculateD(double N0, double N1) {
        return (N1 - N0) / Math.sqrt(bitsLength * 0.95 * 0.05 / 4);
    }
    
    private double calculatePValue(double d) {
        return erfc(Math.abs(d) / Math.sqrt(2));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
