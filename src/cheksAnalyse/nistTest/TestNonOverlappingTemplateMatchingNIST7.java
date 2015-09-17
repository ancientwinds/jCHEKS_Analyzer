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
        
        /*
        From http://jrandtest.sourceforge.net
        The focus of this test is the number of occurrences of pre-defined target substrings. 
        The purpose of this test is to reject sequences that exhibit too many occurrences of a 
        given non-periodic (aperiodic) pattern. For this test and for the Overlapping Template 
        Matching test, an m-bit window is used to search for a specific m-bit pattern. If the 
        pattern is not found, the window slides one bit position. For this test, when the pattern 
        is found, the window is reset to the bit after the found pattern, and the search resumes.
        
        Python code
            n = len(binin)
            m = len(mat)
            M = n/num
            blocks = [binin[xs*M:M+xs*M:] for xs in xrange(n/M)]
            counts = [xx.count(mat) for xx in blocks]
            avg = 1.0 * (M-m+1)/2 ** m
            var = M*(2**-m -(2*m-1)*2**(-2*m))
            chisqr = reduce(su, [(xs - avg) ** 2 for xs in counts]) / var
            pval = spc.gammaincc(1.0 * len(blocks) / 2, chisqr / 2)
            return pval
        */
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
