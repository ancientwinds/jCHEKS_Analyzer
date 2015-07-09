package batchCheksAnalyser;

import Utils.Utils;
import cheksAnalyse.*;
import com.archosResearch.jCHEKS.chaoticSystem.*;
import java.sql.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class MainAnalyser {

    public static void main(String[] args) throws Exception {
        MainAnalyser analyser = new MainAnalyser(10, 50);
        analyser.analyse();
        //brent();

    }

    private Connection connection;
    private Statement statement;
    private final int iterations;
    private final int byteCount;

    public MainAnalyser(int iterations, int byteCount) {
        this.connection = null;
        this.statement = null;
        this.iterations = iterations;
        this.byteCount = byteCount;
    }

    public void analyse() throws Exception {
        this.initDatabase();
        for (int i = 0; i < iterations; i++) {
            ChaoticSystem system = new ChaoticSystem(byteCount * Byte.SIZE);

            CheksAnalyserBooleans boolAnalyser = new CheksAnalyserBooleans(false, system, this.byteCount * Byte.SIZE);
            CheksAnalyserBytesPerBytes bytesAnalyser = new CheksAnalyserBytesPerBytes(false, system, this.byteCount);
            while (true) {
                if (!boolAnalyser.isComplete()) {
                    boolAnalyser.analyse();
                }
                if (!bytesAnalyser.isComplete()) {
                    bytesAnalyser.analyse();
                }
                if(boolAnalyser.isComplete() && bytesAnalyser.isComplete()){
                    break;
                }else{
                    system.evolveSystem();
                }
                       
            }

            saveKeyBits(boolAnalyser.getEvolutionCount());
            saveAgentLevel(bytesAnalyser.getEvolutionCount());
            /*
             system = backup.cloneSystem();
             // #3 - Evolve system 1 000 000 times and for each agent:
             byte[] lastKey = system.getKey();
             byte[] currentKey;
             int[] variations;
             int[][] occurences;
             for (int j = 0; j < 1000000; j++) {
             system.evolveSystem();
             currentKey = system.getKey();
             // - Compute the level variation between each evolution
             for (int agentIndex = 0; agentIndex < byteCount; agentIndex++) {
             variations[j] = currentKey[agentIndex] - lastKey[agentIndex];
             occurences[j][agentIndex]
             }
             // - Compute the number of occurence of each level.
                
                
             saveVariations(j, variations);
             saveOccurences(j, occurences);
             }
            
             system = backup.cloneSystem();
             // #4 - Analyse the number of evolution to see a duplicated key.
             analyser = new CheksAnalyserBooleans(true, system);
             //    - Store the result in a db.
             */
        }
        
        displayStatsOfATable("keybits");
        displayStatsOfATable("agentLevels");
        // - Compute as whole and build the 2 distributions graphs of #3
        // - Compute basic stats of #4
    }
    
    private void displayStatsOfATable(String tableName) throws SQLException{
        double[] evolutions = getEvolutionsOf(tableName);
        System.out.println("|------STATS-OF-AGENTLEVELS----|");
        System.out.println("| Sum: " + Utils.getSumInArray(evolutions));
        System.out.println("| Average: " + Utils.getAverageInArray(evolutions));
        System.out.println("| Minimum: " + Utils.getMinimumInArray(evolutions));
        System.out.println("| Maximum: " + Utils.getMaximumInArray(evolutions));
        System.out.println("| Median: " + Utils.getMedianInArray(evolutions));
        System.out.println("| Standart deviation: " + Utils.getStandartDeviationInArray(evolutions));
        System.out.println("|------------------------------|");
    }
    private void initDatabase() {

        try {
            openDatabase();
            createTableKeyBits();
            createTableAgentLevel();
            /*
             for (int i = 0; i < iterations; i++) {
             createTableOccurences(i);
             createTableVariation(i);
             }
             */
        } catch (Exception e) {
            System.err.println("Could not init db.");
        }
    }

    private void openDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:keys.db");
        this.statement = connection.createStatement();
        System.out.println("Opened database successfully");
    }

    private void createTableKeyBits() throws SQLException {
        this.statement.executeUpdate("CREATE TABLE keybits (evolutions NUMERIC)");
        System.out.println("Create table keyBits");
    }

    private void createTableAgentLevel() throws SQLException {
        this.statement.executeUpdate("CREATE TABLE agentlevels (evolutions NUMERIC)");
        System.out.println("Create table agentLevel");
    }

    private void saveKeyBits(int evolutions) throws SQLException {
        this.statement.executeUpdate("INSERT INTO keybits (evolutions) VALUES (" + evolutions + ")");
    }

    private void saveAgentLevel(int evolutions) throws SQLException {
        this.statement.executeUpdate("INSERT INTO agentlevels (evolutions) VALUES (" + evolutions + ")");
    }
    
    private double[] getEvolutionsOf(String tableName) throws SQLException{
        ResultSet ruleSet = statement.executeQuery( "SELECT * FROM " + tableName + ";" );
        double[] evolutions = new double[iterations];
        for (int i = 0; i < iterations; i++) {
            ruleSet.next();
            evolutions[i] = ruleSet.getInt("evolutions");
        }
        return evolutions;
    }
     
    /*
     private void createTableOccurences(int id) throws SQLException {
     StringBuilder stringBuilder = new StringBuilder("CREATE TABLE occurences");
     stringBuilder.append(id);
     stringBuilder.append(" (");
     for (int i = 0; i < byteCount; i++) {
     stringBuilder.append("agent");
     stringBuilder.append(i);
     stringBuilder.append(" NUMERIC");
     if (i <= 31) {
     stringBuilder.append(",");
     } else {
     stringBuilder.append(")");
     }
     }
     this.statement.executeUpdate(stringBuilder.toString());
     System.out.println(stringBuilder.toString());
     }

     private void createTableVariation(int systemId) throws SQLException {
     StringBuilder stringBuilder = new StringBuilder("CREATE TABLE variations");
     stringBuilder.append(systemId);
     stringBuilder.append(" (");
     for (int i = 0; i < byteCount; i++) {
     stringBuilder.append("agent");
     stringBuilder.append(i);
     stringBuilder.append(" NUMERIC");
     if (i <= 31) {
     stringBuilder.append(",");
     } else {
     stringBuilder.append(")");
     }
     }
     this.statement.executeUpdate(stringBuilder.toString());
     System.out.println(stringBuilder.toString());

     }
    
     private void saveOccurences(int iteration, int[] levelsOccurences){
        
     }
    
     private void saveVariation(int iteration, int[] levelsVariations){
        
     }
     */

    private static int brent() throws Exception {
        ChaoticSystem turtle = new ChaoticSystem(128);
        ChaoticSystem rabbit = turtle.clone();
        int rabbitState = 0;
        int steps_taken = 0;
        int step_limit = 2;
        while (true) {
            rabbit.evolveSystem();
            rabbitState++;
            steps_taken++;
            if (rabbit.equals(turtle)) {
                System.out.println("CYCLE FOUND AT: " + rabbitState);
                return rabbitState;
            }
            if (steps_taken == step_limit) {
                steps_taken = 0;
                step_limit *= 2;
                turtle = rabbit.clone();
                System.out.println(rabbitState);
            }
        }
    }

}
