package rmi.model;

import cheksAnalyse.AbstractCheksAnalyser;
import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import java.io.File;
import java.util.*;
import mainAnalyser.SQLiteSaver;
import rmi.AnalyserPackage;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class Model extends ModelObservable{
    private final HashSet<AnalyserPackage> packagesToSend = new HashSet();
    private final HashMap<String, AnalyserPackage> packagesAnalysing = new HashMap();
    private HashSet<String> systems = new HashSet();
    private final SQLiteSaver saver;
    private final HashSet<AnalyserType> types;
    
    private int analyserGiven = 0;
       
    public Model(HashSet<AnalyserType> types) {
        this.types = types;
        this.saver = new SQLiteSaver();
        this.saver.initDatabase(this.types);
    }
    
    public HashSet<AnalyserPackage> getPackagesToSend() {
        return this.packagesToSend;
    }
    
    public int getTypesLength() {
        return this.types.size();
    }
    
    public void loadSystems(String folderName) {
        File folder = new File(folderName);
        File[] listOfFiles = folder.listFiles();
        HashSet<String> filesName = new HashSet();
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                filesName.add(listOfFile.getName());
            }
        } 
        this.systems = filesName;
        System.out.println(this.systems.size());
    }
    
    public void loadPackages() {
        this.packagesToSend.clear();
        while(!this.systems.isEmpty()) {
            for(Iterator<String> iterator = this.systems.iterator(); iterator.hasNext();) {
                String systemId = iterator.next();
                HashSet<AbstractCheksAnalyser.AnalyserType> typeToAnalyse = new HashSet();
                String sysId = systemId.split("\\.")[0];
                this.types.stream().forEach((type) -> {      
                    if(this.shouldAnalyse(type, sysId)) {
                        typeToAnalyse.add(type);
                    }
                });
                if(typeToAnalyse.size() > 0) {
                    this.packagesToSend.add(new AnalyserPackage(typeToAnalyse, systemId));  
                }
                iterator.remove();
            }
        }        
        notifyLoadingPackagesCompleted(this.packagesToSend);
        System.out.println(this.packagesToSend.size());
    }
    
    private boolean shouldAnalyse(AnalyserType type, String systemId) {
        /*switch (type) {
            case BYTESPERBYTES:
                return !this.saver.isTestRunnedForSystem(TestNbEvolutionsAllAgentLevels.TABLE_NAME, systemId);
            case BOOLEANS:
                return !this.saver.isTestRunnedForSystem(TestNbEvolutionsAllKeyBits.TABLE_NAME, systemId);
            case BUTTERFLY:
                return !this.saver.isTestRunnedForSystem(TestButterflyEffect.TABLE_NAME, systemId);
            case OCCURENCE:
                return !this.saver.isTestRunnedForSystem(TestNbOccurrencesLevel.TABLE_NAME, systemId);
            case VARIATION:
                return !this.saver.isTestRunnedForSystem(TestNbOccurrencesLevelVariation.TABLE_NAME, systemId);
            case NIST_1:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_2:
                return !this.saver.isTestRunnedForSystem(TestFrequencyBlockNIST2.TABLE_NAME, systemId);
            case NIST_3:
                return !this.saver.isTestRunnedForSystem(TestRunsNIST3.TABLE_NAME, systemId);
            case NIST_4:
                return !this.saver.isTestRunnedForSystem(TestLongestRunNIST4.TABLE_NAME, systemId);
            case NIST_5:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_6:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_7:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_8:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_9:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_10:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_11:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_12:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_13:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_14:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case NIST_15:
                return !this.saver.isTestRunnedForSystem(TestFrequencyMonobitNIST1.TABLE_NAME, systemId);
            case KEY_REPETITION:
                return !this.saver.isTestRunnedForSystem(TestKeyRepetition.TABLE_NAME, systemId);
        }*/
        return false;
    }

    public AnalyserPackage getNextPackage() {
        Iterator<AnalyserPackage> iterator = this.packagesToSend.iterator();
        AnalyserPackage packToSend = iterator.next();
        iterator.remove();
        
        System.out.println("Sending system: " + packToSend.systemId + " with analyse: " + packToSend.types.size());

        this.analyserGiven++;
        if(this.analyserGiven % 100 == 0) {
            System.out.println("##################################");
            System.out.println("      " + this.analyserGiven + " given!!!    ");
            System.out.println("##################################");
        }
        
        this.packagesAnalysing.put(packToSend.systemId, packToSend);
        this.packagesToSend.remove(packToSend);
        
        notifyLoadingPackagesCompleted(packagesToSend);
        notifyPackageAnalysingUpdated(this.packagesAnalysing);
        return packToSend;
    }
    
    public void saveAnalyser(AbstractCheksAnalyser analyser, String systemId, AnalyserType type) {
        analyser.saveResult(saver);  
        this.packagesAnalysing.get(systemId).types.remove(type);
        notifyPackageAnalysingUpdated(this.packagesAnalysing);
    }
    
    public HashMap<String, AnalyserPackage> getPackagesAnalysing() {
        return this.packagesAnalysing;
    }
    
    public void analyserPackageDone(AnalyserPackage analyserPackage) {
        this.packagesAnalysing.remove(analyserPackage.systemId);
        notifyPackageAnalysingUpdated(this.packagesAnalysing);

    }
    
   
}