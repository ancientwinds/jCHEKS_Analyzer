package cheksAnalyse.nistTest;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.AbstractSaver;
import static org.apache.commons.math3.special.Erf.erfc;
/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestDiscreteFourierTransformNIST6 extends AbstractNistTest {
    
    public static String TABLE_NAME = "Discrete_Fourier_Transform_NIST_6";
    public static final int BITS_NEEDED = 100000;
    
    public TestDiscreteFourierTransformNIST6(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        throw new Exception("Not supported");
    }
    
    public TestDiscreteFourierTransformNIST6(AbstractChaoticSystem chaoticSystem, int bitsNeeded) throws Exception {
        super(chaoticSystem, bitsNeeded);        
        throw new Exception("Not supported");
    }

    @Override
    public void executeTest(boolean[] bits) {
        double[] sequence = this.transformSequence(bits);
        
        //TODO find a correct implementation for the Discrete Fourier Transformation.
        double[] x = Utils.calculateDiscreteFourierTransformation(sequence);    
        //TODO Be sure it's return the correct result. I could not verify it because of the previous step.
        double[] m = this.calculateM(x);
        double t = this.calculateT();
        double n0 = this.calculateN0();
        double n1 = this.calculateN1(m, t);        
        double d = this.calculateD(n0, n1);
        this.pValue = this.calculatePValue(d);
        
        this.passed = this.pValue > 0.01; 
    }
    
    public double[] calculateM(double[] x) {
        
        double[] m = new double[this.bitsNeeded/2 + 1];
        
        m[0] = Math.sqrt(x[0] * x[0]);

        for(int i = 0; i < this.bitsNeeded / 2; i++) {
            if(2 * i + 2 >= x.length) {
                m[i + 1] = Math.sqrt(Math.pow(x[2 * i + 1], 2));
            } else {
                m[i + 1] = Math.sqrt(Math.pow(x[2 * i + 1], 2) + Math.pow(x[2 * i + 2], 2));
            }
        }
        return m;
    }
    
    public double calculateT() {
        return Math.sqrt(2.995732274 * this.bitsNeeded);
    }
    
    public double[] transformSequence(boolean[] bits) {
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
    
    public double calculateN0() {
        return 0.95 * this.bitsNeeded / 2;
    }
    
    public double calculateN1(double[] m, double t) {
        double N1 = 0;
        
        for(int i = 0; i < m.length; i++) {
            if(m[i] < t) {
                N1++;
            }
        }
        
        return N1;
    }
    
    public double calculateD(double N0, double N1) {
        return (N1 - N0) / Math.sqrt(this.bitsNeeded * 0.95 * 0.05 / 4);
    }
    
    public double calculatePValue(double d) {
        return erfc(Math.abs(d) / Math.sqrt(2));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
