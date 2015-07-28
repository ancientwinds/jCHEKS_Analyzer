package mainAnalyser;

import cheksAnalyse.AbstractCheksAnalyser;
import cheksAnalyse.distanceTest.TestDistanceBetweenEvolution;
import cheksAnalyse.distanceTest.butterflyEffect.TestButterflyEffect;
import cheksAnalyse.evolutionTest.TestNbEvolutionsAllAgentLevels;
import cheksAnalyse.evolutionTest.TestNbEvolutionsAllKeyBits;
import cheksAnalyse.nistTest.TestFrequencyBlockNIST2;
import cheksAnalyse.nistTest.TestFrequencyMonobitNIST1;
import cheksAnalyse.nistTest.TestLongestRunNIST4;
import cheksAnalyse.nistTest.TestRunsNIST3;
import cheksAnalyse.occurenceTest.TestNbOccurrencesLevel;
import cheksAnalyse.occurenceTest.TestNbOccurrencesLevelVariation;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class MySQLSaver extends AbstractSaver{
    
    private final String url = "jdbc:mysql://xo6.x10hosting.com:2083";
    private final String username = "krakenx2_analyse";
    private final String password = "135246TL";
    
    public MySQLSaver() {
        this.connection = null;
        this.statement = null;
    }
 
    @Override
    public void initDatabase(HashSet<AbstractCheksAnalyser.AnalyserType> types) {
        try {
            this.openDatabase();
            
            for(AbstractCheksAnalyser.AnalyserType type : types) {
                switch (type) {
                    case BYTESPERBYTES:
                        this.createEvolutionTable(TestNbEvolutionsAllAgentLevels.TABLE_NAME);
                        break;
                    case BOOLEANS:
                        this.createEvolutionTable(TestNbEvolutionsAllKeyBits.TABLE_NAME);
                        break;
                    case BUTTERFLY:
                        this.createButterflyEffectTable(TestButterflyEffect.TABLE_NAME);
                        break;
                    case OCCURENCE:
                        this.createOccurenceTable(TestNbOccurrencesLevel.TABLE_NAME);
                        break;
                    case VARIATION:
                        this.createOccurenceTable(TestNbOccurrencesLevelVariation.TABLE_NAME);
                        break;
                    case NIST_1:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_2:
                        this.createNistTable(TestFrequencyBlockNIST2.TABLE_NAME);
                        break;
                    case NIST_3:
                        this.createNistTable(TestRunsNIST3.TABLE_NAME);
                        break;
                    case NIST_4:
                        this.createNistTable(TestLongestRunNIST4.TABLE_NAME);
                        break;
                    case NIST_5:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_6:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_7:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_8:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_9:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_10:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_11:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_12:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_13:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_14:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case NIST_15:
                        this.createNistTable(TestFrequencyMonobitNIST1.TABLE_NAME);
                        break;
                    case DISTANCE_EVOLUTION:
                        this.createDistanceTable(TestDistanceBetweenEvolution.TABLE_NAME);
                        break;
                }
            }
            
            this.connection.commit(); 
        } catch (Exception ex) {
            Logger.getLogger(SQLiteSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void openDatabase() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            this.connection.setAutoCommit(false);
            this.statement = connection.createStatement();
            
        } catch (SQLException ex) {
            System.err.println("Error while opening the database: " + ex.getMessage());
        }
    }
    
    @Override
    protected void closeDatabase() {
        try {
            this.statement.close();
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public double[] getEvolutionsOf(String tableName, int iterations) throws SQLException {
        this.statement = connection.createStatement();
        ResultSet ruleSet = statement.executeQuery("SELECT * FROM " + tableName + ";");
        double[] evolutions = new double[iterations];
        for (int i = 0; i < iterations; i++) {
            ruleSet.next();
            evolutions[i] = ruleSet.getInt("evolutions");
        }
        this.statement.close();
        return evolutions;
    }
    
    @Override
    protected void deleteTable(String tableName) {
        try {
            System.out.println("Dropping table " + tableName);
            this.statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
            this.statement.close();
            this.connection.commit();
        } catch (SQLException ex) {
            System.err.println("Error while deleting table: " + tableName);
        }
    }
     
    @Override
    public void cleanDataBase(HashSet<AbstractCheksAnalyser.AnalyserType> types) {
        this.openDatabase();
        
        for(AbstractCheksAnalyser.AnalyserType type : types) {
                switch (type) {
                    case BYTESPERBYTES:
                        this.deleteTable(TestNbEvolutionsAllAgentLevels.TABLE_NAME);                        
                        break;
                    case BOOLEANS:
                        this.deleteTable(TestNbEvolutionsAllKeyBits.TABLE_NAME);                        
                        break;
                    case BUTTERFLY:
                        this.deleteTable(TestButterflyEffect.TABLE_NAME);                        
                        break;
                    case OCCURENCE:
                        this.deleteTable(TestNbOccurrencesLevel.TABLE_NAME);                        
                        break;
                    case VARIATION:
                        this.deleteTable(TestNbOccurrencesLevelVariation.TABLE_NAME);                        
                        break;
                    case NIST_1:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
                        break;
                    case NIST_2:
                        this.deleteTable(TestFrequencyBlockNIST2.TABLE_NAME);                        
                        break;
                    case NIST_3:
                        this.deleteTable(TestRunsNIST3.TABLE_NAME);                        
                        break;
                    case NIST_4:
                        this.deleteTable(TestLongestRunNIST4.TABLE_NAME);                        
                        break;
                    case NIST_5:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case NIST_6:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case NIST_7:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case NIST_8:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case NIST_9:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case NIST_10:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case NIST_11:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case NIST_12:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case NIST_13:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case NIST_14:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case NIST_15:
                        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
                        break;
                    case DISTANCE_EVOLUTION:
                        this.deleteTable(TestDistanceBetweenEvolution.TABLE_NAME);                       
                        break;
                }
            }
        this.closeDatabase();        
    }
     
    @Override
    public boolean isTestRunnedForSystem(String tableName, String systemId) {
        try {
            int count;
            try (PreparedStatement selectStatement = this.connection.prepareStatement("SELECT COUNT(*) AS rowcount FROM " + tableName + " WHERE chaotic_system_id = ?")) {
                selectStatement.setString(1, systemId);
                ResultSet rs = selectStatement.executeQuery();
                if(rs == null) {
                    this.closeDatabase();
                    System.out.println("error");
                    return false;
                }   count = rs.getInt("rowcount");
            }
            
            return count > 0;
        } catch (SQLException ex) {
            System.err.println("Error while checking if test: " + tableName +" was run for system: " + systemId);
            return false;
        }
    }
    
    public static void main(String[] args) throws Exception {
        SQLiteSaver saver = new SQLiteSaver();
        
        HashSet<AbstractCheksAnalyser.AnalyserType> types = new HashSet();
        
        types.add(AbstractCheksAnalyser.AnalyserType.BOOLEANS);
        types.add(AbstractCheksAnalyser.AnalyserType.BYTESPERBYTES);
        types.add(AbstractCheksAnalyser.AnalyserType.BUTTERFLY);
        types.add(AbstractCheksAnalyser.AnalyserType.OCCURENCE);
        //types.add(AnalyserType.VARIATION);
        types.add(AbstractCheksAnalyser.AnalyserType.NIST_1);
        types.add(AbstractCheksAnalyser.AnalyserType.NIST_2);
        types.add(AbstractCheksAnalyser.AnalyserType.NIST_3);
        types.add(AbstractCheksAnalyser.AnalyserType.NIST_4);
        //types.add(AnalyserType.DISTANCE_EVOLUTION);
        
        saver.cleanDataBase(types);
    }
}
