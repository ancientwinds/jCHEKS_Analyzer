package mainAnalyser;

import cheksAnalyse.AbstractCheksAnalyser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public abstract class AbstractSaver {
    
    protected Connection connection;
    protected Statement statement;    

    public abstract void initDatabase(HashSet<AbstractCheksAnalyser.AnalyserType> types);     
    public abstract void cleanDataBase(HashSet<AbstractCheksAnalyser.AnalyserType> types);
    public abstract boolean isTestRunnedForSystem(String tableName, String systemId);
    
    protected abstract void openDatabase();
    protected abstract void closeDatabase();    
    protected abstract void createNistTable(String tableName); 
    protected abstract void createEvolutionTable(String tableName);     
    protected abstract void createButterflyEffectTable(String tableName);     
    protected abstract void createDistanceTable(String tableName); 
    protected abstract void createOccurenceTable(String tableName);
    protected abstract void deleteTable(String tableName);

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
            System.out.println(ex.getMessage());
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
                insertStatement.setInt(3, distances[i]);
                insertStatement.executeUpdate(); 
            }
            this.connection.commit();
            if (insertStatement != null) insertStatement.close();

        } catch (SQLException ex) {
            System.err.println("Error while inserting " + tableName + " result for system: " + systemId);
        }
    }
    
}
