package cheksAnalyse.NIST;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 * 
 * NIST Test 2.4: Longest Run in a Block
 */
public class NistTest4 extends AbstractNistTest{

    private int blockLength = 128;

    public NistTest4() {
        super();
        this.bitsNeeded = 100000;
    }
    
    public NistTest4(int bitsNeeded, int blockLength) {
        super();
        this.bitsNeeded = bitsNeeded;
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
    
}
