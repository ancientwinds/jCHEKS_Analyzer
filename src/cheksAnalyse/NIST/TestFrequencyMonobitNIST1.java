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
public class TestFrequencyMonobitNIST1 extends AbstractNistTest{
    
    public static String TABLE_NAME = "FrequencyMonobit_NIST_1";
    
    public TestFrequencyMonobitNIST1(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, 100000);
    }
    
    public TestFrequencyMonobitNIST1(AbstractChaoticSystem chaoticSystem, int bitsNeeded) throws Exception {
        super(chaoticSystem, bitsNeeded);
    }

    @Override
    public void executeTest(boolean[] bits) {
        int Sn = this.calculateSn(bits);
        double Sobs = this.calculateSobs(bits, Math.abs(Sn));

        this.pValue = this.calculatePValue(Sobs, Sn);
        this.passed = this.pValue > 0.01;        
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
        return Math.abs(Sn) / Math.sqrt(bit.length);
    }
    
    public double calculatePValue(double Sobs, int Sn) {
        return erfc(Sobs / Math.sqrt(2));
    }

    @Override
    public void saveResult(Saver saver) {
        saver.saveNistResults(this.getSystemId(), TABLE_NAME, pValue);
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
