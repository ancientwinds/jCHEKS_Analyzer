package postProcessing;

import java.sql.*;
import java.util.HashSet;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class PostProcessor {
    
    public static void main(String... args) throws Exception {
        new PostProcessor();
    }
    
    private Connection connection;
    private Statement statement;
    
    public PostProcessor() throws ClassNotFoundException, SQLException{
        connectToDatabase();
        //overallButterfly();
        overallOccurenceLevelAgent();
        overallOccurenceVariation();
    }
    
    private void connectToDatabase() throws ClassNotFoundException, SQLException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:complete-128.db");
        connection.setAutoCommit(false);
    }
    
    private void overallButterfly() throws SQLException{
        statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS overall_butterfly (chaotic_system_id TEXT, groupIndex DOUBLE, overallSum DOUBLE, PRIMARY KEY (chaotic_system_id, groupIndex))");
        statement.close();
        
        ResultSet resultSet = statement.executeQuery("SELECT chaotic_system_id, evolution_count, sum(distance) AS evolution_sum FROM butterfly_effect GROUP BY chaotic_system_id, evolution_count");
        while (resultSet.next()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO overall_butterfly (chaotic_system_id, groupIndex, overallSum) VALUES (?,?,?)");
            insertStatement.setString(1, resultSet.getString("chaotic_system_id"));
            insertStatement.setDouble(2, resultSet.getInt("distance"));
            insertStatement.setDouble(3, resultSet.getInt("evolution_sum"));
            insertStatement.executeUpdate();
        }   
        connection.commit();
    }
    
    private void overallOccurenceVariation() throws SQLException{
        statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS overall_nbOccurrences_levelVariation (chaotic_system_id TEXT, groupIndex DOUBLE, overallSum DOUBLE, PRIMARY KEY (chaotic_system_id, groupIndex))");
        statement.close();
        
        ResultSet resultSet = statement.executeQuery("SELECT chaotic_system_id, variation, sum(occurence_count) AS occurrence_sum FROM nbOccurrences_levelVariation GROUP BY chaotic_system_id, variation");
        while (resultSet.next()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO overall_nbOccurrences_levelVariation (chaotic_system_id, groupIndex, overallSum) VALUES (?,?,?)");
            insertStatement.setString(1, resultSet.getString("chaotic_system_id"));
            insertStatement.setDouble(2, resultSet.getInt("variation"));
            insertStatement.setDouble(3, resultSet.getInt("occurrence_sum"));
            insertStatement.executeUpdate();
        }   
        connection.commit();
    }
    
    private void overallOccurenceLevelAgent() throws SQLException{
        statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS overall_nbOccurrences_level (chaotic_system_id TEXT, groupIndex DOUBLE, overallSum DOUBLE, PRIMARY KEY (chaotic_system_id, groupIndex))");
        statement.close();
        
        ResultSet resultSet = statement.executeQuery("SELECT chaotic_system_id, variation, sum(occurence_count) AS occurrence_sum FROM nbOccurrences_level GROUP BY chaotic_system_id, variation");
        while (resultSet.next()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO overall_nbOccurrences_level (chaotic_system_id, groupIndex, overallSum) VALUES (?,?,?)");
            insertStatement.setString(1, resultSet.getString("chaotic_system_id"));
            insertStatement.setDouble(2, resultSet.getInt("variation"));
            insertStatement.setDouble(3, resultSet.getInt("occurrence_sum"));
            insertStatement.executeUpdate();
        }   
        connection.commit();
    }
}