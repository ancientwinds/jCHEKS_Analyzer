package cheksAnalyse.butterfly;

import cheksAnalyse.NIST.AbstractNistTest;
import static org.apache.commons.math3.special.Erf.erfc;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 * 
 * NIST Test 2.1: Frequency Monobit
 */
public class NistTest1 extends AbstractNistTest{
    
    public NistTest1() {
        super();
        this.bitsNeeded = 100000;
    }
    
    public NistTest1(int bitsNeeded) {
        super();
        this.bitsNeeded = bitsNeeded;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        int Sn = this.calculateSn(bits);
        double Sobs = this.calculateSobs(bits, Sn);
        double pValue = this.calculatePValue(Sobs, Sn);
        
        this.passed = pValue > 0.01;
        
    }
    
    public int calculateSn(boolean[] bits) {
        int count = 0;
                
        for(int i = 0; i < bits.length; i++) {
            if(bits[i] == true) {
                count++;
            } else {
                count--;
            }
        }
        
        return count;
    }
    
    public double calculateSobs(boolean[] bit, int Sn) {
        return Sn / Math.sqrt(bit.length);
    }
    
    public double calculatePValue(double Sobs, int Sn) {
        return erfc(Sobs / Math.sqrt(Sn));
    }
    
}
