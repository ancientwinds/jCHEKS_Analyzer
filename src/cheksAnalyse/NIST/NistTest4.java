package cheksAnalyse.NIST;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.Saver;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 * 
 * NIST Test 2.4: Longest Run in a Block
 */
public class NistTest4 extends AbstractNistTest{

    private int blockLength = 128;

    public static String TABLE_NAME = "LongestRun_NIST_4";
    
    public NistTest4(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, 100000);
        this.bitsNeeded = 100000;
    }
    
    public NistTest4(AbstractChaoticSystem chaoticSystem, int bitsNeeded, int blockLength) throws Exception {
        super(chaoticSystem, bitsNeeded);
        this.blockLength = blockLength;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void saveResult(Saver saver) {
        saver.saveNistResults(this.getSystemId(), TABLE_NAME, pValue);
    }
    
}
