package cheksAnalyse;

import java.util.BitSet;
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
    public void executeTest(BitSet bits) {
        int Sn = this.calculateSn(bits);
        double Sobs = this.calculateSobs(bits, Sn);
        double pValue = this.calculatePValue(Sobs, Sn);
        
        if(pValue >= 0.01) {
            this.passed = true;
        }
    }
    
    public int calculateSn(BitSet bits) {
        int count = 0;
                
        for(int i = 0; i < bits.length(); i++) {
            if(bits.get(i) == true) {
                count++;
            } else {
                count--;
            }
        }
        
        return count;
    }
    
    public double calculateSobs(BitSet bit, int Sn) {
        return Sn / Math.sqrt(bit.length());
    }
    
    public double calculatePValue(double Sobs, int Sn) {
        return erfc(Sobs / Math.sqrt(Sn));
    }
    
}
