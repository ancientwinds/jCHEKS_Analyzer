package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.AbstractSaver;

/**
 *
 * @author Michael Roussel rousselm4@gmail.com
 */
public class TestOverlappingTemplateMatchingNIST8 extends AbstractNistTest{

    
    public static final int BITS_NEEDED = 0; 
    public static final String TABLE_NAME = "overlapping_template_matching_NIST_8";
    
    public TestOverlappingTemplateMatchingNIST8(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_8;
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
