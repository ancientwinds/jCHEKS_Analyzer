package mainAnalyser;

import cheksAnalyse.*;
import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import com.archosResearch.jCHEKS.chaoticSystem.*;
import com.archosResearch.jCHEKS.chaoticSystem.exception.*;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class MainAnalyser {

    private final AbstractSaver saver; 
    private HashSet<AbstractCheksAnalyser> analysers = new HashSet();
    private HashSet<AnalyserType> types = new HashSet();
    
    
    private static final String FILE_TO_STOP = "removeToStop.stop";
    
    public MainAnalyser(HashSet<AnalyserType> types) {                

        File f = new File(FILE_TO_STOP);
        try {
            f.createNewFile();
        } catch (IOException ex) {
            System.out.println("Could not create file to stop analyse. " + ex.getMessage());
        }
        
        this.types = types;        
        this.saver = new MySQLSaver();
        saver.initDatabase(types);        
    }

    public void analyse() throws Exception {
        int count = 0;
        
        HashSet<String> systemsName = this.getSystemsFileName("system");
        for(Iterator<String> system = systemsName.iterator(); system.hasNext();) {
            if(shouldContinueAnalyse()) {
                String fileName = system.next();
                Date startTime = new Date();
                
                this.analyseSystem(fileName);                
                
                DateFormat dateFormat = new SimpleDateFormat("mm:ss");
                Date date = new Date((new Date()).getTime() - startTime.getTime());
                System.out.println("Analyse of " + fileName + " is done. It took: " + dateFormat.format(date));
                system.remove();                
            } else {
                return;
            }
            count++;
            
        }

        //displayStatsOfADistributionTable("variations");
        //displayStatsOfADistributionTable("occurences");
        //displayStatsOfATable("KEY_BITS");
        //displayStatsOfATable("AGENT_LEVELS");
    }
    
    private boolean analyseWithSystem(AbstractCheksAnalyser analyser, AbstractChaoticSystem system) throws Exception {
        if(!saver.isTestRunnedForSystem(analyser.getTableName(), system.getSystemId())) {
            if(!analyser.isComplete()) {
                analyser.analyse(system);
                return false;
            } else {
                analyser.saveResult(saver);
            }
        }        
        return true;
    }
    
    private void analyseSystem(String systemName) throws Exception {
        AbstractChaoticSystem currentChaoticSystem = FileReader.readChaoticSystem(systemName);
        this.analysers = AbstractCheksAnalyser.createAnalyser(types, currentChaoticSystem);
                
        while(!this.analysers.isEmpty()) {                
            for(Iterator<AbstractCheksAnalyser> iterator = this.analysers.iterator(); iterator.hasNext();) {
                AbstractCheksAnalyser analyser = iterator.next();
                if(this.analyseWithSystem(analyser, currentChaoticSystem)) {
                    iterator.remove();
                }
                
                analyser = null;
            }                
            currentChaoticSystem.evolveSystem();
        }
        this.analysers.clear();
    }
    
    private boolean shouldContinueAnalyse() {
        File f = new File(FILE_TO_STOP);
        return f.exists() && !f.isDirectory();
    }
    /*private void displayStatsOfADistributionTable(String tableName) {
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
        /*double[] evolutions = saver.getEvolutionsOf(tableName, iterations);
        System.out.println("|------STATS-OF-" + tableName.toUpperCase() + "----|");
        System.out.println("| Sum: " + Utils.getSumInArray(evolutions));
        System.out.println("| Average: " + Utils.getAverageInArray(evolutions));
        System.out.println("| Minimum: " + Utils.getMinimumInArray(evolutions));
        System.out.println("| Maximum: " + Utils.getMaximumInArray(evolutions));
        System.out.println("| Median: " + Utils.getMedianInArray(evolutions));
        System.out.println("| Standart deviation: " + Utils.getStandartDeviationInArray(evolutions));
        System.out.println("|------------------------------|");*/
    }

    private static int brent() throws Exception {
        ChaoticSystem turtle = new CryptoChaoticSystem(128, "test");
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

    private void analyseAgentLevelOccurenceBetweenSystem(int agentCount) throws ChaoticSystemException, KeyLenghtException, NoSuchAlgorithmException {
        Distribution[] distributions = new Distribution[agentCount];
        for (int j = 0; j < agentCount; j++) {
            distributions[j] = new Distribution();
        }
        for (int i = 0; i < 1000; i++) {
            ChaoticSystem system = new CryptoChaoticSystem(agentCount * Byte.SIZE, "test");
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
    
    private HashSet<String> getSystemsFileName(String folderName) {
        File folder = new File(folderName);
        File[] listOfFiles = folder.listFiles();
        HashSet<String> filesName = new HashSet();
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                filesName.add(listOfFile.getName());
            }
        } 
        return filesName;
    }
    
    public static void main(String[] args) throws Exception {
        HashSet<AnalyserType> types = new HashSet();
        
        //types.add(AnalyserType.BOOLEANS);
        //types.add(AnalyserType.BYTESPERBYTES);
        //types.add(AnalyserType.BUTTERFLY);
        //types.add(AnalyserType.OCCURENCE);
        //types.add(AnalyserType.VARIATION);
        //types.add(AnalyserType.NIST_1);
        //types.add(AnalyserType.NIST_2);
        //types.add(AnalyserType.NIST_3);
        //types.add(AnalyserType.NIST_4);
        types.add(AnalyserType.DISTANCE_EVOLUTION);        
        
        MainAnalyser analyser = new MainAnalyser(types);
        analyser.analyse();       
    }
    
}
