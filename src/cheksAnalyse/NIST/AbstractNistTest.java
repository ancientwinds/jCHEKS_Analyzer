package cheksAnalyse.NIST;

import cheksAnalyse.AbstractCheksAnalyser;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public abstract class AbstractNistTest extends AbstractCheksAnalyser{
    
    protected boolean passed = false;
    protected int bitsNeeded = 0;
    public static String TABLE_NAME = "";

    public AbstractNistTest(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(false, chaoticSystem);
    }    
    
    public AbstractNistTest(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
    }
    
    public abstract void executeTest(boolean[] bits);
    
    public boolean isPassed() {
        return this.passed;
    }
    
    public int getBitsNeeded() {
        return this.bitsNeeded;
    }
}
