package mainAnalyser;

import cheksAnalyse.*;
import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import com.archosResearch.jCHEKS.chaoticSystem.*;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.io.File;
import java.io.IOException;
import java.text.*;
import java.util.*;

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
        this.saver = new SQLiteSaver();
        saver.initDatabase(types);        
    }

    public void analyse() throws Exception {
        int count = 0;
        
        //HashSet<String> systemsName = this.getSystemsFileName("system");
        HashSet<String> systemsName = this.getSystemsFileName("system/TestRange/range_0-16");
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
        //types.add(AnalyserType.DISTANCE_EVOLUTION);
        types.add(AnalyserType.KEY_REPETITION);    
        
        MainAnalyser analyser = new MainAnalyser(types);
        analyser.analyse();       
    }
    
}
