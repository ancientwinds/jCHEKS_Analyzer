package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;

/**
 *
 * @author Michael Roussel rousselm4@gmail.com
 */
public class TestNonOverlappingTemplateMatchingNIST7 extends AbstractNistTest{

    public static final int BITS_NEEDED = 0; //TODO Set this value
    public static final String TABLE_NAME = "non_overlapping_template_matching_NIST_7";
    
    public TestNonOverlappingTemplateMatchingNIST7(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_7;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        throw new UnsupportedOperationException("Not supported yet.");
        /* 
         * The variable bits is the input bits of the lenght of BITS_NEEDED generated by the chaotic system.
         * This method should change pValue attribute of the result.
         * this.pValue = result;
         */
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
