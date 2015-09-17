package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;

/**
 *
 * @author Michael Roussel rousselm4@gmail.com
 */
public class TestSerialNIST11  extends AbstractNistTest{

    public static final int BITS_NEEDED = 0; //TODO Set this value
    public static final String TABLE_NAME = "serial_NIST_11";
    
    public TestSerialNIST11(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_11;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
