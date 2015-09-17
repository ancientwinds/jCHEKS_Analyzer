package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.AbstractSaver;

/**
 *
 * @author Michael Roussel rousselm4@gmail.com
 */
public class TestNIST7 extends AbstractNistTest{

    
    public TestNIST7(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, 100000);
        this.type = AnalyserType.NIST_1;

    }
    @Override
    public void executeTest(boolean[] bits) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveResult(AbstractSaver saver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTableName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
