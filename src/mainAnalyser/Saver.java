package mainAnalyser;

import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import cheksAnalyse.CheksAnalyserBooleans;
import cheksAnalyse.CheksAnalyserBytesPerBytes;
import cheksAnalyse.CheksAnalyserLevelOccurence;
import cheksAnalyse.CheksAnalyserLevelVariation;
import cheksAnalyse.NIST.*;
import cheksAnalyse.butterfly.CheksButterflyEffectTest;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
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
            openDatabase();
            
            for(AnalyserType type : types) {
                switch (type) {
                    case NIST:
                        break;
                    case BYTESPERBYTES:
                        createEvolutionTable(CheksAnalyserBytesPerBytes.TABLE_NAME);
                        break;
                    case BOOLEANS:
                        createEvolutionTable(CheksAnalyserBooleans.TABLE_NAME);
                        break;
                    case BUTTERFLY:
                        createButterflyEffectTable(CheksButterflyEffectTest.TABLE_NAME);
                        break;
                    case OCCURENCE:
                        createOccurenceTable(CheksAnalyserLevelOccurence.TABLE_NAME);
                        break;
                    case VARIATION:
                        createOccurenceTable(CheksAnalyserLevelVariation.TABLE_NAME);
                        break;
                    case NIST_1:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_2:
                        this.createNistTable(NistTest2.TABLE_NAME);
                        break;
                    case NIST_3:
                        this.createNistTable(NistTest3.TABLE_NAME);
                        break;
                    case NIST_4:
                        this.createNistTable(NistTest4.TABLE_NAME);
                        break;
                    case NIST_5:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_6:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_7:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_8:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_9:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_10:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_11:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_12:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_13:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_14:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                    case NIST_15:
                        this.createNistTable(NistTest1.TABLE_NAME);
                        break;
                }
            }
            
            this.connection.commit();
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
    
    private void createNistTable(String tableName) throws Exception {
        this.statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
        this.statement.executeUpdate("CREATE TABLE " + tableName + " (chaotic_system_id TEXT PRIMARY KEY, p_value NUMERIC)");
    }

    private void createEvolutionTable(String tableName) throws Exception {        
        this.statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
        this.statement.executeUpdate("CREATE TABLE " + tableName + " (chaotic_system_id TEXT PRIMARY KEY, evolution_count DOUBLE)");
    }
    
    private void createButterflyEffectTable(String tableName) throws Exception {
        this.statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
        this.statement.executeUpdate("CREATE TABLE " + tableName + " (chaotic_system_id TEXT, clone_id INTEGER, evolution_count INTEGER, distance INTEGER, PRIMARY KEY (chaotic_system_id, clone_id, evolution_count))");
    }

    private void createOccurenceTable(String tableName) throws Exception {
        this.statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
        this.statement.executeUpdate("CREATE TABLE " + tableName + " (chaotic_system_id TEXT, agent_id INTEGER, variation INTEGER, occurence_count INTEGER, PRIMARY KEY(chaotic_system_id, agent_id, variation))");
    }
    
    public void saveNistResults(String systemId, String tableName, double pValue) {
        try {
            PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO " + tableName + " (chaotic_system_id, p_value) VALUES (?,?)");
            insertStatement.setString(1, systemId);
            insertStatement.setDouble(2, pValue);
            insertStatement.executeUpdate();
            this.connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void saveEvolutionCount(String tableName, String chaoticSystemId, int evolutionCount) {
        try {
            PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO " + tableName + " (chaotic_system_id, evolution_count) VALUES (?,?)");
            insertStatement.setString(1, chaoticSystemId);
            insertStatement.setInt(2, evolutionCount);            
            insertStatement.executeUpdate();
            this.connection.commit();            
        } catch (SQLException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveDistributionInTable(String chaoticSystemId, String tableName, Distribution[] distributions) {
        try {            
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
            
        } catch (SQLException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveButterflyEffect(String systemId, int[][] results) {

        try {
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
        } catch (SQLException ex) {
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

}
