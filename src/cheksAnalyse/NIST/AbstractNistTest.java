package cheksAnalyse.NIST;

import Utils.Utils;
import cheksAnalyse.AbstractCheksAnalyser;
import static cheksAnalyse.NIST.NistTest2.TABLE_NAME;
import static cheksAnalyse.NIST.NistTest4.TABLE_NAME;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.Saver;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public abstract class AbstractNistTest extends AbstractCheksAnalyser{
    
    protected boolean passed = false;
    protected int bitsNeeded = 0;
    protected int bitsCount = 0;
    protected boolean[] bits;
    protected boolean testExecuted = false;
    protected double pValue = 0;

    public AbstractNistTest(AbstractChaoticSystem chaoticSystem, int bitsNeeded) throws Exception {
        super(false, chaoticSystem);
        this.initAnalyser(bitsNeeded);
    }    
    
    public AbstractNistTest(boolean enableLog, AbstractChaoticSystem chaoticSystem, int bitsNeeded) throws Exception {
        super(enableLog, chaoticSystem);
        this.initAnalyser(bitsNeeded);
    }
    
    private void initAnalyser(int bitsNeeded) {
        this.bitsNeeded = bitsNeeded;
        this.bits = new boolean[this.bitsNeeded];
    }
    
    public abstract void executeTest(boolean[] bits);
    
    public boolean isPassed() {
        return this.passed;
    }
    
    public int getBitsNeeded() {
        return this.bitsNeeded;
    }
    
    public void appendKey() {
        byte[] key = this.getKey();
            boolean[] bitsToAdd = Utils.bytesToBooleanArray(key);
            
            if(this.bitsNeeded - this.bitsCount > bitsToAdd.length) {
                System.arraycopy(bitsToAdd, 0, this.bits, bitsCount, bitsToAdd.length);
                this.bitsCount += bitsToAdd.length;
            } else {
                System.arraycopy(bitsToAdd, 0, this.bits, bitsCount, this.bitsNeeded - this.bitsCount);
                this.bitsCount += this.bitsNeeded - this.bitsCount;
            }
    }
    
    @Override
    protected void scan(AbstractChaoticSystem system) {
        if(this.bitsCount == this.bitsNeeded) {
            this.executeTest(bits);
            this.testExecuted = true;
        } else {
            this.appendKey();
        }
    }

    @Override
    protected void verify() {
        if(this.testExecuted) {
            this.complete();
        }
    }
}
