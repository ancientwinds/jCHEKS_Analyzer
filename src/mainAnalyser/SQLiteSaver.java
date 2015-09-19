package mainAnalyser;

import cheksAnalyse.nistTest.*;
import cheksAnalyse.occurenceTest.*;
import cheksAnalyse.evolutionTest.*;
import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import cheksAnalyse.distanceTest.TestDistanceBetweenEvolution;
import cheksAnalyse.distanceTest.butterflyEffect.TestButterflyEffect;
import cheksAnalyse.nistTest.TestBinaryMatrixRankNIST5;
import java.sql.*;
import java.util.HashSet;
import java.util.logging.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class SQLiteSaver extends AbstractSaver{
    
    public SQLiteSaver() {
        this.connection = null;
        this.statement = null;
    }
 
    @Override
    public void initDatabase(HashSet<AnalyserType> types) {
        try {
            this.openDatabase();
            
            for(AnalyserType type : types) {
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
                        this.createNistTable(TestBinaryMatrixRankNIST5.TABLE_NAME);
                        break;
                    case NIST_6:
                        this.createNistTable(TestDiscreteFourierTransformNIST6.TABLE_NAME);
                        break;
                    case NIST_7:
                        this.createNistTable(TestNonOverlappingTemplateMatchingNIST7.TABLE_NAME);
                        break;
                    case NIST_8:
                        this.createNistTable(TestOverlappingTemplateMatchingNIST8.TABLE_NAME);
                        break;
                    case NIST_9:
                        this.createNistTable(TestMaurersUniveralStatisticalNIST9.TABLE_NAME);
                        break;
                    case NIST_10:
                        this.createNistTable(TestLinearComplexityNIST10.TABLE_NAME);
                        break;
                    case NIST_11:
                        this.createNistTable(TestSerialNIST11.TABLE_NAME);
                        break;
                    case NIST_12:
                        this.createNistTable(TestApproximateEntropyNIST12.TABLE_NAME);
                        break;
                    case NIST_13:
                        this.createNistTable(TestCumulativeSumsNIST13.TABLE_NAME);
                        break;
                    case NIST_14:
                        this.createNistTable(TestRandomExcursionsNIST14.TABLE_NAME);
                        break;
                    case NIST_15:
                        this.createNistTable(TestRandomExcursionsVariantNIST15.TABLE_NAME);
                        break;
                    case DISTANCE_EVOLUTION:
                        this.createDistanceTable(TestDistanceBetweenEvolution.TABLE_NAME);
                        break;
                    case CYCLE:
                        this.createEvolutionTable(TestCycle.TABLE_NAME);
                        break;
                    case KEY_REPETITION:
                        this.createEvolutionTable(TestCycle.TABLE_NAME);
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
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:complete.db");
            this.connection.setAutoCommit(false);
            this.statement = connection.createStatement();
        } catch (SQLException ex) {
            System.err.println("Error while opening the database");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLiteSaver.class.getName()).log(Level.SEVERE, null, ex);
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
 
    @Override
    protected void createNistTable(String tableName) {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT PRIMARY KEY, p_value DOUBLE)");
            this.statement.close();
        } catch (SQLException ex) {
            System.err.println("Error while creating table: " + tableName);
        }
    }
    
    @Override
    protected void createEvolutionTable(String tableName){        
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT PRIMARY KEY, evolution_count INTEGER)");
            this.statement.close();
        } catch (SQLException ex) {
            System.err.println("Error while creating table: " + tableName);
        }
    }
    
    @Override
    protected void createButterflyEffectTable(String tableName) {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT, clone_id INTEGER, evolution_count INTEGER, distance INTEGER, PRIMARY KEY (chaotic_system_id, clone_id, evolution_count))");
            this.statement.close();
        } catch (SQLException ex) {
            System.err.println("Error while creating table: " + tableName);
        }
    }
    
    @Override 
    protected void createDistanceTable(String tableName) {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT, evolution_count iINTEGER, distance INTEGER, PRIMARY KEY (chaotic_system_id, evolution_count))");
            this.statement.close();
        } catch (SQLException ex) {
            System.err.println("Error while creating table: " + tableName);
        }
    }
 
    @Override
    protected void createOccurenceTable(String tableName){
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT, agent_id INTEGER, variation INTEGER, occurence_count INTEGER, PRIMARY KEY(chaotic_system_id, agent_id, variation))");
            this.statement.close();
        } catch (SQLException ex) {
            System.err.println("Error while creating table: " + tableName);
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
    public void cleanDataBase(HashSet<AnalyserType> types) {
        this.openDatabase();
        
        for(AnalyserType type : types) {
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
                        this.deleteTable(TestBinaryMatrixRankNIST5.TABLE_NAME);                       
                        break;
                    case NIST_6:
                        this.deleteTable(TestDiscreteFourierTransformNIST6.TABLE_NAME);                       
                        break;
                    case NIST_7:
                        this.deleteTable(TestNonOverlappingTemplateMatchingNIST7.TABLE_NAME);                       
                        break;
                    case NIST_8:
                        this.deleteTable(TestOverlappingTemplateMatchingNIST8.TABLE_NAME);                       
                        break;
                    case NIST_9:
                        this.deleteTable(TestMaurersUniveralStatisticalNIST9.TABLE_NAME);                       
                        break;
                    case NIST_10:
                        this.deleteTable(TestLinearComplexityNIST10.TABLE_NAME);                       
                        break;
                    case NIST_11:
                        this.deleteTable(TestSerialNIST11.TABLE_NAME);                       
                        break;
                    case NIST_12:
                        this.deleteTable(TestApproximateEntropyNIST12.TABLE_NAME);                       
                        break;
                    case NIST_13:
                        this.deleteTable(TestCumulativeSumsNIST13.TABLE_NAME);                       
                        break;
                    case NIST_14:
                        this.deleteTable(TestRandomExcursionsNIST14.TABLE_NAME);                       
                        break;
                    case NIST_15:
                        this.deleteTable(TestRandomExcursionsVariantNIST15.TABLE_NAME);                       
                        break;
                    case DISTANCE_EVOLUTION:
                        this.deleteTable(TestDistanceBetweenEvolution.TABLE_NAME);                       
                        break;
                    case CYCLE:
                        this.deleteTable(TestCycle.TABLE_NAME);                       
                        break;
                    case KEY_REPETITION:
                        this.deleteTable(TestCycle.TABLE_NAME);                       
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
                }
                count = rs.getInt("rowcount");
            }
            
            return count > 1;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //System.err.println("Error while checking if test: " + tableName +" was run for system: " + systemId);
            return false;
        }
    }
    
    public static void main(String[] args) throws Exception {
        SQLiteSaver saver = new SQLiteSaver();
        
        HashSet<AnalyserType> types = new HashSet();
        types.add(AnalyserType.KEY_REPETITION);
        //types.add(AnalyserType.BOOLEANS);
        //types.add(AnalyserType.BYTESPERBYTES);
        //types.add(AnalyserType.BUTTERFLY);
        //types.add(AnalyserType.OCCURENCE);
        //types.add(AnalyserType.VARIATION);
        //types.add(AnalyserType.NIST_1);
        //types.add(AnalyserType.NIST_2);
        //types.add(AnalyserType.NIST_3);
        //types.add(AnalyserType.NIST_4);
        //types.add(AnalyserType.DISTANCE_EVOLUTION);
        
        saver.cleanDataBase(types);
    }
}
