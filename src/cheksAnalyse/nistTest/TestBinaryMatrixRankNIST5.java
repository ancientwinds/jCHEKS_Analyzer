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
    
    private final int rowsMatrix = 32;
    private final int columnsMatrix = 32;
    
    public TestBinaryMatrixRankNIST5(AbstractChaoticSystem chaoticSystem, int bitsNeeded) throws Exception {
        super(chaoticSystem, 100000);
        this.type = AnalyserType.NIST_5;
    }

    @Override
    public void executeTest(boolean[] bits) {
        boolean[][][] matrices = Utils.createMatrices(bits, rowsMatrix, columnsMatrix);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
