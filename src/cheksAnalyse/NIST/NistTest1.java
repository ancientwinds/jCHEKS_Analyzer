package cheksAnalyse.NIST;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.Saver;
import static org.apache.commons.math3.special.Erf.erfc;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 * 
 * NIST Test 2.1: Frequency Monobit
 */
public class NistTest1 extends AbstractNistTest{
    
    public NistTest1(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem);
        this.bitsNeeded = 100000;
        NistTest1.TABLE_NAME = "FrequencyMonobit_NIST-1";
    }
    
    public NistTest1(AbstractChaoticSystem chaoticSystem, int bitsNeeded) throws Exception {
        super(chaoticSystem);
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

    @Override
    protected void scan(AbstractChaoticSystem system) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void verify() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveResult(Saver saver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
