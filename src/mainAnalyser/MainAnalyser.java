package mainAnalyser;

import Utils.Utils;
import cheksAnalyse.*;
import com.archosResearch.jCHEKS.chaoticSystem.*;
import com.archosResearch.jCHEKS.chaoticSystem.exception.CloningException;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.sql.*;
import java.util.Arrays;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class MainAnalyser {

    public static void main(String[] args) throws Exception {
        brent();
        brent();
        brent();
        brent();
        brent();
        brent();
        brent();
        brent();
        brent();
        brent();
        brent();
        //MainAnalyser analyser = new MainAnalyser(10, 32);
        //analyser.analyse();
    }
    private Distribution[] agentsLevelsOccurencesDistributions;
    private final Distribution[] agentsLevelsVariationsDistributions;
    private Connection connection;
    private Statement statement;
    private final int iterations;
    private final int byteCount;
    private ChaoticSystem currentChaoticSystem;
    private ChaoticSystem backupChaoticSystem;
    private CheksAnalyserBooleans currentBoolAnalyser;
    private CheksAnalyserBytesPerBytes currentBytesAnalyser;
    private byte[] lastKey;
    private byte[] currentKey;

    public MainAnalyser(int iterations, int byteCount) {
        this.connection = null;
        this.statement = null;
        this.iterations = iterations;
        this.byteCount = byteCount;
        agentsLevelsOccurencesDistributions = new Distribution[byteCount];
        agentsLevelsVariationsDistributions = new Distribution[byteCount];
        reinitDistributions();
    }

    public void analyse() throws Exception {
        this.initDatabase();
        for (int i = 0; i < iterations; i++) {
            setCurrentSystemAndAnalyser();
            performEvolutionDependantAnalyse();
            saveEvolutionDependantAnalyse();
            reinitChaoticSystem();

            lastKey = currentChaoticSystem.getKey();

            for (int j = 0; j <= 100000; j++) {
                currentChaoticSystem.evolveSystem();
                currentKey = currentChaoticSystem.getKey();
                for (int k = 0; k < byteCount; k++) {
                    int modifier = 1;
            
                 
                    //if(
                    //currentKey[k] +  lastKey[k]);
                    //System.out.println("    result: " + x +"    result: " + y +"    key: " +lastKey[k] + "     lk: " + currentKey[k]);
                    
                    
                    //agentsLevelsVariationsDistributions[k].registerValue(Math.abs(x)<Math.abs(y)?Math.abs(x):Math.abs(y));
                    agentsLevelsOccurencesDistributions[k].registerValue(currentKey[k]);
                }
                lastKey = currentKey;
            }
            saveOccurences();
            saveVariations();
            reinitDistributions();

        }
        displayStatsOfATable("keybits");
        displayStatsOfATable("agentLevels");
    }

    private void displayStatsOfATable(String tableName) throws SQLException {
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

    private void initDatabase(){
        openDatabase();
        createTableKeyBits();
        createTableAgentLevel();
        createDistributionTable("variations");
        createDistributionTable("occurences");
    }

    private void openDatabase(){
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:keys.db");
            this.statement = connection.createStatement();
        } catch (ClassNotFoundException ex) {
            System.err.println("Error while trying to find class.");
        } catch (SQLException ex) {
            System.err.println("Error while connecting to database.");
        }
    }

    private void createTableKeyBits(){
        try {
            this.statement.executeUpdate("CREATE TABLE keybits (evolutions NUMERIC)");
        } catch (SQLException ex) {
            System.err.println("Error while creating keybits table.");
        }
    }

    private void createTableAgentLevel(){
        try {
            this.statement.executeUpdate("CREATE TABLE agentlevels (evolutions NUMERIC)");
        } catch (SQLException ex) {
            System.err.println("Error while creating agentlevels table.");
        }
    }

    private void performEvolutionDependantAnalyse() {
        while (true) {
            if (!currentBoolAnalyser.isComplete()) {
                currentBoolAnalyser.analyse();
            }
            if (!currentBytesAnalyser.isComplete()) {
                currentBytesAnalyser.analyse();
            }
            if (currentBoolAnalyser.isComplete() && currentBytesAnalyser.isComplete()) {
                break;
            } else {
                currentChaoticSystem.evolveSystem();
            }
        }
    }

    private void reinitChaoticSystem() {
        try {
            this.currentChaoticSystem = this.backupChaoticSystem.cloneSystem();
        } catch (CloningException ex) {
            System.err.println("Error while cloning system.");
        }
    }

    private void saveEvolutionDependantAnalyse() {
        saveKeyBits();
        saveAgentLevel();
    }

    private void saveKeyBits() {
        try {
            this.statement.executeUpdate("INSERT INTO keybits (evolutions) VALUES (" + currentBoolAnalyser.getEvolutionCount() + ")");
        } catch (SQLException ex) {
            System.err.println("Error while saving to keybits.");
        }
    }

    private void saveAgentLevel() {
        try {
            this.statement.executeUpdate("INSERT INTO agentlevels (evolutions) VALUES (" + currentBytesAnalyser.getEvolutionCount() + ")");
        } catch (SQLException ex) {
            System.err.println("Error while saving to agentlevel.");
        }
    }

    private void saveOccurences() {
        try {
            saveDistribution("occurences", agentsLevelsOccurencesDistributions);
        } catch (SQLException ex) {
            System.err.println("Error while saving to occurences.");
        }
    }

    private void saveVariations() {
        try {
            saveDistribution("variations", agentsLevelsVariationsDistributions);
        } catch (SQLException ex) {
            System.err.println("Error while saving to variations.");
        }
    }

    private void saveDistribution(String tableName, Distribution[] distributions) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        stringBuilder.append(tableName);
        stringBuilder.append(" )");
        for (int i = 0; i < byteCount; i++) {
            stringBuilder.append("agent");
            stringBuilder.append(i);
            if (i < byteCount - 1) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append(")");
            }
        }
        stringBuilder.append(" VALUES (");
        for (int i = 0; i < byteCount; i++) {
            stringBuilder.append("\"");
            stringBuilder.append(distributions[i].toString());
            stringBuilder.append("\"");
            if (i < byteCount - 1) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append(")");
            }
        }
        System.out.println(stringBuilder.toString());
        this.statement.executeUpdate(stringBuilder.toString());
    }

    private double[] getEvolutionsOf(String tableName) throws SQLException {
        ResultSet ruleSet = statement.executeQuery("SELECT * FROM " + tableName + ";");
        double[] evolutions = new double[iterations];
        for (int i = 0; i < iterations; i++) {
            ruleSet.next();
            evolutions[i] = ruleSet.getInt("evolutions");
        }
        return evolutions;
    }

    private void createDistributionTable(String tableName){
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
        stringBuilder.append(tableName);
        stringBuilder.append(" (");
        for (int i = 0; i < byteCount; i++) {
            stringBuilder.append("agent");
            stringBuilder.append(i);
            stringBuilder.append(" TEXT");
            if (i < byteCount - 1) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append(")");
            }
        }
        try {
            this.statement.executeUpdate(stringBuilder.toString());
        } catch (SQLException ex) {
            System.err.println("Error while creating the distribution table for " + tableName);
        }
    }

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

    private void setCurrentSystemAndAnalyser() throws Exception {
        currentChaoticSystem = new ChaoticSystem(byteCount * Byte.SIZE);
        currentBoolAnalyser = new CheksAnalyserBooleans(false, currentChaoticSystem, this.byteCount * Byte.SIZE);
        currentBytesAnalyser = new CheksAnalyserBytesPerBytes(false, currentChaoticSystem, this.byteCount);
        backupChaoticSystem = currentChaoticSystem.cloneSystem();
    }

    private void reinitDistributions() {
        for (int j = 0; j < byteCount; j++) {
            agentsLevelsOccurencesDistributions[j] = new Distribution(256);
            agentsLevelsVariationsDistributions[j] = new Distribution(128);
        }
    }

    private void analyseAgentLevelOccurenceBetweenSystem(int agentCount) throws ChaoticSystemException {
        Distribution[] distributions = new Distribution[agentCount];
        for (int j = 0; j < agentCount; j++) {
            distributions[j] = new Distribution(256);
        }
        for (int i = 0; i < 1000; i++) {
            ChaoticSystem system = new ChaoticSystem(agentCount * Byte.SIZE);
            byte[] key = system.getKey();
            for (int k = 0; k < agentCount; k++) {
                distributions[k].registerValue(key[k]);
            }
            if (i % 10 == 0) {
                System.out.println(Arrays.toString(distributions));
            }
        }
        System.out.println(Arrays.toString(Distribution.getSum(distributions, 256)));
    }
}
