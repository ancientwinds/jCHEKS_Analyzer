package cheksAnalyse;

import java.util.BitSet;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public abstract class AbstractNistTest {
    
    protected boolean passed = false;
    protected int bitsNeeded = 0;
    
    public abstract void executeTest(boolean[] bits);
    
    public boolean isPassed() {
        return this.passed;
    }
    
    public int getBitsNeeded() {
        return this.bitsNeeded;
    }
}
