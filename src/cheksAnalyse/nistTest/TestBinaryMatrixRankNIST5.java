package cheksAnalyse.nistTest;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.AbstractSaver;

/**
 *
 * @author Thomas
 */
public class TestBinaryMatrixRankNIST5 extends AbstractNistTest{

    public static String TABLE_NAME = "Binary_Matrix_Rank_NIST_5";
    
    private int rowsMatrix = 32;
    private int columnsMatrix = 32;
    
    public TestBinaryMatrixRankNIST5(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, 100000);
        this.type = AnalyserType.NIST_5;
    }
    
    public TestBinaryMatrixRankNIST5(AbstractChaoticSystem chaoticSystem, int bitsNeeded, int rows, int cols) throws Exception {
        super(chaoticSystem, bitsNeeded);
        this.type = AnalyserType.NIST_5;
        
        this.rowsMatrix = rows;
        this.columnsMatrix = cols;        
    }

    @Override
    public void executeTest(boolean[] bits) {
        boolean[][][] matrices = Utils.createMatrices(bits, rowsMatrix, columnsMatrix);
        
        int[] ranks = this.calculteRanks(matrices);
        double xObs = this.calculateXobs(ranks);
        this.pValue = this.calculatePValue(xObs);
        
        this.passed = pValue > 0.01;
    }
    
    public double calculateXobs(int[] ranks) {
        int k = this.bitsNeeded / (this.rowsMatrix * this.columnsMatrix);
        int Fm = this.countMatrixWithRank(ranks, this.rowsMatrix);
        int Fm1 = this.countMatrixWithRank(ranks, this.rowsMatrix - 1);
        int remaining = ranks.length - Fm - Fm1;
        
        double xObs = (Math.pow(((double)Fm - (0.2888 * (double)k)), 2) / (0.2888 * (double)k));
        xObs += (Math.pow(((double)Fm1 - (0.5776 * (double)k)), 2) / (0.5776 * (double)k)); 
        xObs += (Math.pow(((double)remaining - (0.1336 * (double)k)), 2) / (0.1336 * (double)k));
        return xObs;
    }
    
    public double calculatePValue(double xObs) {
        return Math.pow(Math.E, (-xObs/(double)2));
    }
    
    public int countMatrixWithRank(int[] ranks, int rank) {
        int count = 0;
        for(int i = 0; i < ranks.length; i++) {
            if(ranks[i] == rank) {
                count++;
            }
        }
        
        return count;
    }
    
    public int calculateRank(boolean[][] matrix) {
        boolean[][] prepared = Utils.prepareMatrix(matrix);
        int rank = 0;
        
        for(int i = 0; i < prepared.length; i++) {
            if(prepared[i][i]) {
                rank++;
            }
        }
        
        return rank;
    }
    
    public int[] calculteRanks(boolean[][][] matrices) {
        int ranks[] = new int[matrices.length];
        
        for(int i = 0; i < matrices.length; i++) {
            ranks[i] = this.calculateRank(matrices[i]);
        }
        
        return ranks;
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
