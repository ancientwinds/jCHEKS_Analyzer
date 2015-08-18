package mainAnalyser;

import cheksAnalyse.AbstractCheksAnalyser;
import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.io.*;
import java.text.*;
import java.util.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public abstract class AbstractMainAnalyser {
    
    protected final AbstractSaver saver; 
    protected HashSet<AbstractCheksAnalyser> analysers = new HashSet();
    protected HashSet<AnalyserType> types = new HashSet();  
        
    protected static final String FILE_TO_STOP = "removeToStop.stop";

    public AbstractMainAnalyser(HashSet<AnalyserType> types) {
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
    
    protected abstract void analyse() throws Exception;
    
    protected boolean analyseWithSystem(AbstractCheksAnalyser analyser, AbstractChaoticSystem system) throws Exception {
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
    
    protected abstract void analyseSystem(String systemName) throws Exception;
    
    protected boolean shouldContinueAnalyse() {
        File f = new File(FILE_TO_STOP);
        return f.exists() && !f.isDirectory();
    } 
    
    protected List<String> getSystemsFileName(String folderName) {
        File folder = new File(folderName);
        File[] listOfFiles = folder.listFiles();
        List<String> filesName = new ArrayList();
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                filesName.add(listOfFile.getName());
            }
        } 
        return filesName;
    }
}
