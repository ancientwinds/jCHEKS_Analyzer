package mainAnalyser;

import Utils.Utils;
import cheksAnalyse.*;
import com.archosResearch.jCHEKS.chaoticSystem.*;
import com.archosResearch.jCHEKS.chaoticSystem.exception.CloningException;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class MainAnalyser {

    public static void main(String[] args) throws Exception {
        MainAnalyser analyser = new MainAnalyser(10, 32);
        analyser.analyse();
    }
    private final Distribution[] agentsLevelsOccurencesDistributions;
    private final Distribution[] agentsLevelsVariationsDistributions;
    private final Saver saver;
    private final int iterations;
    private final int numberOfAgent;
    private ChaoticSystem currentChaoticSystem;
    private ChaoticSystem backupChaoticSystem;
    private CheksAnalyserBooleans currentBoolAnalyser;
    private CheksAnalyserBytesPerBytes currentBytesAnalyser;
    private byte[] lastKey;
    private byte[] currentKey;

    public MainAnalyser(int iterations, int numberOfAgent) {

        this.iterations = iterations;
        this.numberOfAgent = numberOfAgent;
        this.saver = new Saver(numberOfAgent);
        saver.initDatabase();
        agentsLevelsOccurencesDistributions = new Distribution[numberOfAgent];
        agentsLevelsVariationsDistributions = new Distribution[numberOfAgent];
        reinitDistributions();
    }

    public void analyse() throws Exception {

        for (int i = 0; i < iterations; i++) {
            setCurrentSystemAndAnalyser();
            performEvolutionDependantAnalyse();
            saveRelativeEvolutionResults();
            reinitChaoticSystem();
            performDistributionAnalyse();
            saveDistributionsResults();
            reinitDistributions();
        }
        //displayStatsOfADistributionTable("variations");
        //displayStatsOfADistributionTable("occurences");
        displayStatsOfATable("keybits");
        displayStatsOfATable("agentLevels");
    }
/*
    private void displayStatsOfADistributionTable(String tableName) {
        try {
            
            Distribution[][] systems = saver.getDistributionsOf(tableName, iterations);
            int[] sums[] = new int[systems.length][numberOfAgent];
            for (int i = 0; i < systems.length; i++) {
                sums[i] = Distribution.getSum(systems[i]);
            }
            System.out.println(Arrays.toString(sums));
            
        } catch (SQLException ex) {
            Logger.getLogger(MainAnalyser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    private void displayStatsOfATable(String tableName) throws SQLException {
        double[] evolutions = saver.getEvolutionsOf(tableName, iterations);
        System.out.println("|------STATS-OF-" + tableName.toUpperCase() + "----|");
        System.out.println("| Sum: " + Utils.getSumInArray(evolutions));
        System.out.println("| Average: " + Utils.getAverageInArray(evolutions));
        System.out.println("| Minimum: " + Utils.getMinimumInArray(evolutions));
        System.out.println("| Maximum: " + Utils.getMaximumInArray(evolutions));
        System.out.println("| Median: " + Utils.getMedianInArray(evolutions));
        System.out.println("| Standart deviation: " + Utils.getStandartDeviationInArray(evolutions));
        System.out.println("|------------------------------|");
    }

    private int getVariationByAgent(int agent) {
        int currentLevel = currentKey[agent] + 128;
        int lastLevel = lastKey[agent] + 128;
        int variation = currentLevel - lastLevel;
        if (variation < -128 || variation > 127) {
            return (256 - Math.abs(variation)) * ((variation < 0) ? 1 : -1);
        } else {
            return variation;
        }
    }

    private void performDistributionAnalyse() {
        lastKey = currentChaoticSystem.getKey();
        for (int j = 0; j <= 100000; j++) {
            currentChaoticSystem.evolveSystem();
            currentKey = currentChaoticSystem.getKey();
            for (int k = 0; k < numberOfAgent; k++) {
                agentsLevelsVariationsDistributions[k].registerValue(getVariationByAgent(k));
                agentsLevelsOccurencesDistributions[k].registerValue(currentKey[k]);
            }
            lastKey = currentKey;
        }
    }

    private void saveRelativeEvolutionResults() {
        saver.saveValueInTable("keybits", currentBoolAnalyser.getEvolutionCount());
        saver.saveValueInTable("agentlevels", currentBytesAnalyser.getEvolutionCount());
    }

    private void saveDistributionsResults() {
        saver.saveDistributionInTable("occurences", agentsLevelsOccurencesDistributions);
        saver.saveDistributionInTable("variations", agentsLevelsVariationsDistributions);
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
            Logger.getLogger(MainAnalyser.class.getName()).log(Level.SEVERE, null, ex);
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
        currentChaoticSystem = new ChaoticSystem(numberOfAgent * Byte.SIZE);
        currentBoolAnalyser = new CheksAnalyserBooleans(false, currentChaoticSystem, this.numberOfAgent * Byte.SIZE);
        currentBytesAnalyser = new CheksAnalyserBytesPerBytes(false, currentChaoticSystem, this.numberOfAgent);
        backupChaoticSystem = currentChaoticSystem.cloneSystem();
    }

    private void reinitDistributions() {
        for (int j = 0; j < numberOfAgent; j++) {
            agentsLevelsOccurencesDistributions[j] = new Distribution();
            agentsLevelsVariationsDistributions[j] = new Distribution();
        }
    }

    private void analyseAgentLevelOccurenceBetweenSystem(int agentCount) throws ChaoticSystemException {
        Distribution[] distributions = new Distribution[agentCount];
        for (int j = 0; j < agentCount; j++) {
            distributions[j] = new Distribution();
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
        System.out.println(Arrays.toString(Distribution.getSum(distributions)));
    }
}
