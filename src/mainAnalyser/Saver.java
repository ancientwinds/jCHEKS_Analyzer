package mainAnalyser;

import cheksAnalyse.nistTest.TestLongestRunNIST4;
import cheksAnalyse.nistTest.TestFrequencyBlockNIST2;
import cheksAnalyse.nistTest.TestRunsNIST3;
import cheksAnalyse.nistTest.TestFrequencyMonobitNIST1;
import cheksAnalyse.occurenceTest.TestNbOccurrencesLevelVariation;
import cheksAnalyse.occurenceTest.TestNbOccurrencesLevel;
import cheksAnalyse.evolutionTest.TestNbEvolutionsAllKeyBits;
import cheksAnalyse.evolutionTest.TestNbEvolutionsAllAgentLevels;
import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import cheksAnalyse.distanceTest.TestDistanceBetweenEvolution;
import cheksAnalyse.distanceTest.butterflyEffect.TestButterflyEffect;
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
                    case DISTANCE_EVOLUTION:
                        this.createDistanceTable(TestDistanceBetweenEvolution.TABLE_NAME);
                        break;
                }
            }
            
            this.connection.commit(); 
        } catch (Exception ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void openDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:keys.db");
            this.connection.setAutoCommit(false);
            this.statement = connection.createStatement();
        } catch (SQLException ex) {
            System.err.println("Error while opening the database");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void closeDatabase() {
        try {
            this.statement.close();
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createNistTable(String tableName) {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT PRIMARY KEY, p_value NUMERIC)");
            this.statement.close();
        } catch (SQLException ex) {
            System.err.println("Error while creating table: " + tableName);
        }
    }

    private void createEvolutionTable(String tableName){        
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT PRIMARY KEY, evolution_count DOUBLE)");
            this.statement.close();
        } catch (SQLException ex) {
            System.err.println("Error while creating table: " + tableName);
        }
    }
    
    private void createButterflyEffectTable(String tableName) {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT, clone_id INTEGER, evolution_count INTEGER, distance INTEGER, PRIMARY KEY (chaotic_system_id, clone_id, evolution_count))");
            this.statement.close();
        } catch (SQLException ex) {
            System.err.println("Error while creating table: " + tableName);
        }
    }
    
    private void createDistanceTable(String tableName) {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT, evolution_count INTEGER, distance INTEGER, PRIMARY KEY (chaotic_system_id, evolution_count))");
            this.statement.close();
        } catch (SQLException ex) {
            System.err.println("Error while creating table: " + tableName);
        }
    }

    private void createOccurenceTable(String tableName){
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (chaotic_system_id TEXT, agent_id INTEGER, variation INTEGER, occurence_count INTEGER, PRIMARY KEY(chaotic_system_id, agent_id, variation))");
            this.statement.close();
        } catch (SQLException ex) {
            System.err.println("Error while creating table: " + tableName);
        }
    }
    
    public void saveNistResults(String systemId, String tableName, double pValue) {
        try {
            try (PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO " + tableName + " (chaotic_system_id, p_value) VALUES (?,?)")) {
                insertStatement.setString(1, systemId);
                insertStatement.setDouble(2, pValue);
                insertStatement.executeUpdate();
                this.connection.commit();
            }
        } catch (Exception ex) {
            System.err.println("Error while inserting Nist result for system: " + systemId + " on test: " + tableName);
        }
    }
    
    public void saveEvolutionCount(String tableName, String chaoticSystemId, int evolutionCount) {
        try {
            try (PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO " + tableName + " (chaotic_system_id, evolution_count) VALUES (?,?)")) {
                insertStatement.setString(1, chaoticSystemId);
                insertStatement.setInt(2, evolutionCount);
                insertStatement.executeUpdate();
                this.connection.commit();
            }

        } catch (Exception ex) {
            System.err.println("Error while inserting evolutions count result for system: " + chaoticSystemId + " on table: " + tableName);
        }
    }

    public void saveDistributionInTable(String chaoticSystemId, String tableName, Distribution[] distributions) {
        try {
            for(int i = 0; i < distributions.length; i++) {
                for(int j = 0; j < distributions[i].getAgentLevels().length; j++) {
                    try (PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO " + tableName + " (chaotic_system_id, agent_id, variation, occurence_count) VALUES (?,?,?,?)")) {
                        insertStatement.setString(1, chaoticSystemId);
                        insertStatement.setInt(2, i);
                        insertStatement.setInt(3, j);
                        insertStatement.setInt(4, distributions[i].getAgentLevels()[j]);
                        insertStatement.executeUpdate();
                    }
                }
            }
            this.connection.commit();

        } catch (SQLException ex) {
            System.err.println("Error while inserting distribution result for system: " + chaoticSystemId + " on table: " + tableName);
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
            if (insertStatement != null) insertStatement.close();

        } catch (SQLException ex) {
            System.err.println("Error while inserting butterfly effect result for system: " + systemId);
        }
    }
    
    public void saveDistance(String systemId, String tableName, int[] distances) {
        try {
            PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO " + tableName + " (chaotic_system_id, evolution_count, distance) VALUES (?,?,?)");
            for (int i = 0; i < distances.length; i ++) {                
                insertStatement.setString(1, systemId);
                insertStatement.setInt(2, i);
                insertStatement.setInt(4, distances[i]);
                insertStatement.executeUpdate(); 
            }
            this.connection.commit();
            if (insertStatement != null) insertStatement.close();

        } catch (SQLException ex) {
            System.err.println("Error while inserting " + tableName + " result for system: " + systemId);
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
    
    private void deleteTable(String tableName) {
        try {
            System.out.println("Dropping table " + tableName);
            this.statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
            this.statement.close();
            this.connection.commit();
        } catch (SQLException ex) {
            System.err.println("Error while deleting table: " + tableName);
        }
    }
    
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
        Saver saver = new Saver();
        
        HashSet<AnalyserType> types = new HashSet();
        
        types.add(AnalyserType.BOOLEANS);
        types.add(AnalyserType.BYTESPERBYTES);
        types.add(AnalyserType.BUTTERFLY);
        types.add(AnalyserType.OCCURENCE);
        //types.add(AnalyserType.VARIATION);
        types.add(AnalyserType.NIST_1);
        types.add(AnalyserType.NIST_2);
        types.add(AnalyserType.NIST_3);
        types.add(AnalyserType.NIST_4);
        //types.add(AnalyserType.DISTANCE_EVOLUTION);
        
        saver.cleanDataBase(types);
    }
}
