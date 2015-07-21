package cheksAnalyse.NIST;

import static org.apache.commons.math3.special.Gamma.regularizedGammaQ;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 * 
 * NIST Test 2.2: Frequency Block Test
 */

public class NistTest2 extends AbstractNistTest{

    private int blockLength = 2000;
    
    public NistTest2() {
        super();
        this.bitsNeeded = 100000;
    }
    
    public NistTest2(int bitsNeeded, int blockLength) {
        super();
        this.bitsNeeded = bitsNeeded;
        this.blockLength = blockLength;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        boolean[][] blocks = this.partitionBits(bits);
        double[] proportions = this.calculateProportion(blocks);
        double[] ratios = this.calculateRatio(proportions);
        double xObs = this.calculateXobs(ratios);
        double pValue = this.calculatePValue(xObs);
        
        this.passed = pValue > 0.01;
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
    
    public double[] calculateProportion(boolean[][] blocks) {
        double proportions[] = new double[blocks.length];
        
        for(int i = 0; i < blocks.length; i++) {
            double ones = 0;
            
            for(int j = 0; j < blocks[i].length; j++) {
                if(blocks[i][j] == true) {
                    ones++;
                }
            }            
            proportions[i] = ones/blocks[i].length;
        }
        
        return proportions;
    }
    
    public double[] calculateRatio(double[] proportions) {
        double ratios[] = new double[proportions.length];
        
        for(int i = 0; i < proportions.length; i++) {
            ratios[i] = Math.pow((proportions[i] - (double)1/(double)2), 2);
        }
        
        return ratios;
    }
    
    public double calculateXobs(double[] ratios) {
        double xObs = 0;
        
        double totalRatios = 0;
        for(int i = 0; i < ratios.length; i++) {
            totalRatios += ratios[i];
        }
        
        xObs = 4 * this.blockLength * totalRatios;
        
        return xObs;
    }
    
    public double calculatePValue(double xObs) {
        int blockCount = this.bitsNeeded/this.blockLength;
       
        return regularizedGammaQ((double)blockCount/2, xObs/2);
    }
    
}