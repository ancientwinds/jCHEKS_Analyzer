package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.AbstractSaver;

/**
 *
 * @author Michael Roussel rousselm4@gmail.com
 */
public class TestRandomExcursionsNIST14 extends AbstractNistTest{

    public static final int BITS_NEEDED = 0; //TODO Set this value
    public static final String TABLE_NAME = "random_excursions_NIST_14";
    
    public TestRandomExcursionsNIST14(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_14;
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
