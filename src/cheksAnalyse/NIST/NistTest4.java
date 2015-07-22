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
    
    public boolean[][] partitionBits(boolean[] bits) {        
        
        boolean blockBits[][] = new boolean[bits.length/this.blockLength][this.blockLength];
        
        for(int i = 0; i < bits.length/this.blockLength; i++) {
            for(int j = 0; j < this.blockLength; j++) {
                blockBits[i][j] = bits[j + (i * this.blockLength)]; 
            }
        }

        return blockBits;
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

    @Override
    public void saveResult(Saver saver) {
        saver.saveNistResults(this.getSystemId(), TABLE_NAME, pValue);
    }
    
}
