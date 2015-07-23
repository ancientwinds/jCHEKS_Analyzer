package mainAnalyser;

import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import cheksAnalyse.*;
import cheksAnalyse.NIST.*;
import cheksAnalyse.butterfly.TestButterflyEffect;
import java.sql.*;
import java.util.HashSet;
import java.util.logging.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Saver {

    private Connection connection;
    private Statement statement;

    public Saver() {
        this.connection = null;
        this.statement = null;
    }

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
                }
            }
            
            this.connection.commit(); 
            this.closeDatabase();
        } catch (Exception ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void openDatabase() throws Exception {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:keys.db");
        this.connection.setAutoCommit(false);
        this.statement = connection.createStatement();
    }
    
    private void closeDatabase() {
        try {
            this.statement.close();
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createNistTable(String tableName) throws Exception {
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT PRIMARY KEY, p_value NUMERIC)");
    }

    private void createEvolutionTable(String tableName) throws Exception {        
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT PRIMARY KEY, evolution_count DOUBLE)");
    }
    
    private void createButterflyEffectTable(String tableName) throws Exception {
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT, clone_id INTEGER, evolution_count INTEGER, distance INTEGER, PRIMARY KEY (chaotic_system_id, clone_id, evolution_count))");
    }

    private void createOccurenceTable(String tableName) throws Exception {
        this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT, agent_id INTEGER, variation INTEGER, occurence_count INTEGER, PRIMARY KEY(chaotic_system_id, agent_id, variation))");
    }
    
    public void saveNistResults(String systemId, String tableName, double pValue) {
        try {
            this.openDatabase();
            PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO " + tableName + " (chaotic_system_id, p_value) VALUES (?,?)");
            insertStatement.setString(1, systemId);
            insertStatement.setDouble(2, pValue);
            insertStatement.executeUpdate();
            this.connection.commit();
            this.closeDatabase();
        } catch (Exception ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    public void saveEvolutionCount(String tableName, String chaoticSystemId, int evolutionCount) {
        try {
            this.openDatabase();
            PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO " + tableName + " (chaotic_system_id, evolution_count) VALUES (?,?)");
            insertStatement.setString(1, chaoticSystemId);
            insertStatement.setInt(2, evolutionCount);            
            insertStatement.executeUpdate();
            this.connection.commit();
            this.closeDatabase();
        } catch (Exception ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveDistributionInTable(String chaoticSystemId, String tableName, Distribution[] distributions) {
        try {
            this.openDatabase();
            for(int i = 0; i < distributions.length; i++) {
                for(int j = 0; j < distributions[i].getAgentLevels().length; j++) {
                    PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO " + tableName + " (chaotic_system_id, agent_id, variation, occurence_count) VALUES (?,?,?,?)");
                    insertStatement.setString(1, chaoticSystemId);
                    insertStatement.setInt(2, i);
                    insertStatement.setInt(3, j);
                    insertStatement.setInt(4, distributions[i].getAgentLevels()[j]);
                    insertStatement.executeUpdate();
                }
            }
            this.connection.commit();
            this.closeDatabase();
        } catch (Exception ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveButterflyEffect(String systemId, int[][] results) {
        try {
            this.openDatabase();
            PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO butterfly_effect (chaotic_system_id, clone_id, evolution_count, distance) VALUES (?,?,?,?)");
            for (int i = 0; i < results.length; i ++) { 
                for (int j = 0; j < results[i].length; j++) {
                    insertStatement.setString(1, systemId);
                    insertStatement.setInt(2, i);
                    insertStatement.setInt(3, j);
                    insertStatement.setInt(4, results[i][j]);
                    insertStatement.executeUpdate(); 
                }
            }
            this.connection.commit();
            this.closeDatabase();
        } catch (Exception ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double[] getEvolutionsOf(String tableName, int iterations) throws SQLException {
        ResultSet ruleSet = statement.executeQuery("SELECT * FROM " + tableName + ";");
        double[] evolutions = new double[iterations];
        for (int i = 0; i < iterations; i++) {
            ruleSet.next();
            evolutions[i] = ruleSet.getInt("evolutions");
        }
        return evolutions;
    }
/*
    public int[][][] getDistributionsOf(String tableName, int iterations) throws SQLException {
        ResultSet ruleSet = statement.executeQuery("SELECT * FROM " + tableName + ";");
        int[][][] systems = new int[iterations][numberOfAgent][numberOfAgent * Byte.SIZE];
        for (int i = 0; i < iterations; i++) {
            ruleSet.next();
            int[][] system = systems[i];
            for (int j = 0; j < numberOfAgent; j++) {
                system[j] = ;
                String[] stringValues = getArrayFromString(ruleSet.getString("agent" + j));
                for (String stringValue : stringValues) {
                    int value = Integer.parseInt(stringValue);
                    
                }

            }
        }
        return systems;
    }*/

    private String[] getArrayFromString(String serializedArray) {
        return serializedArray.replace("[", "").replace("]", "").split(", ");
    }
    
    private void deleteTable(String tableName) throws SQLException {
        System.out.println("Dropping table " + tableName);
        this.statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
        this.connection.commit();
    }
    
    public void cleanDataBase() throws Exception {
        this.openDatabase();
        
        this.deleteTable(TestNbEvolutionsAllAgentLevels.TABLE_NAME);                        
        this.deleteTable(TestNbEvolutionsAllKeyBits.TABLE_NAME);                        
        this.deleteTable(TestButterflyEffect.TABLE_NAME);                        
        this.deleteTable(TestNbOccurrencesLevel.TABLE_NAME);                        
        this.deleteTable(TestNbOccurrencesLevelVariation.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
        this.deleteTable(TestFrequencyBlockNIST2.TABLE_NAME);                        
        this.deleteTable(TestRunsNIST3.TABLE_NAME);                        
        this.deleteTable(TestLongestRunNIST4.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                        
        this.deleteTable(TestFrequencyMonobitNIST1.TABLE_NAME);                       
        
        this.closeDatabase();        
    }
    
    public boolean isTestRunnedForSystem(String tableName, String systemId) throws Exception {
        this.openDatabase();
        PreparedStatement selectStatement = this.connection.prepareStatement("SELECT COUNT(*) AS rowcount FROM " + tableName + " WHERE chaotic_system_id = ?");
        selectStatement.setString(1, systemId);
        
        ResultSet rs = selectStatement.executeQuery();
        int count = rs.getInt("rowcount");
        this.closeDatabase();       
        
        return count > 0;
    }
    
    public static void main(String[] args) throws Exception {
        Saver saver = new Saver();
        saver.cleanDataBase();
    }
}
