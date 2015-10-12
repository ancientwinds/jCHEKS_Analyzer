package cheksAnalyse.nistTest;

import cheksAnalyse.AbstractCheksAnalyser;
import java.util.HashSet;
import mainAnalyser.*;


public class MockNISTSaver extends AbstractSaver {
    private double pValue;

    public double getPValue(){
        return pValue;
    }
    
    @Override
    public void saveNistResults(String systemId, String tableName, double pValue) {
        this.pValue = pValue;
    }  
    
    @Override
    public void initDatabase(HashSet<AbstractCheksAnalyser.AnalyserType> types) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void cleanDataBase(HashSet<AbstractCheksAnalyser.AnalyserType> types) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean isTestRunnedForSystem(String tableName, String systemId) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    protected void openDatabase() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    protected void closeDatabase() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    protected void createNistTable(String tableName) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    protected void createEvolutionTable(String tableName) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    protected void createButterflyEffectTable(String tableName) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    protected void createDistanceTable(String tableName) {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    protected void createOccurenceTable(String tableName) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    protected void deleteTable(String tableName) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
       

    @Override
    public void saveEvolutionCount(String tableName, String chaoticSystemId, int evolutionCount) {
        throw new UnsupportedOperationException("Not supported.");
    }
 
    @Override
    public void saveDistributionInTable(String chaoticSystemId, String tableName, Distribution[] distributions) {
        throw new UnsupportedOperationException("Not supported.");
    }
     
    @Override
    public void saveButterflyEffect(String systemId, int[][] results) {
        throw new UnsupportedOperationException("Not supported.");
    }
     
    @Override
    public void saveDistance(String systemId, String tableName, int[] distances) {       
        throw new UnsupportedOperationException("Not supported.");
    }
    
}
