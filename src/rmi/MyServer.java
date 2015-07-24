package rmi;

import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import static cheksAnalyse.AbstractCheksAnalyser.AnalyserType.*;
import cheksAnalyse.NIST.*;
import cheksAnalyse.*;
import cheksAnalyse.butterfly.TestButterflyEffect;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import mainAnalyser.Saver;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Server;
/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class MyServer extends Server implements IServer{

    private Saver saver;
    private HashSet<AnalyserType> types;
    private HashSet<String> systems;

    private int analyserGiven = 0;    
    HashSet<Package> packagesToSend = new HashSet();
    
    public MyServer(HashSet<AnalyserType> types, int port) {
        
        this.types = types;
        this.saver = new Saver();
        this.saver.initDatabase(types);
        
        System.out.println("Preparing packages to analyse...");
        this.systems = this.getSystemsFileName("system");
        this.packagesToSend = this.generatePackage();
        System.out.println("Packages to send: " + this.packagesToSend.size());  
        
        try {
            CallHandler callHandler = new CallHandler();
            callHandler.registerGlobal(IServer.class, this);
            this.bind(10000, callHandler);
            System.out.println("Listening on port: " + port);
            System.out.println("Awaiting client to send analyser...");
            while(true) {
                Thread.sleep(500);
            }
        } catch (LipeRMIException | IOException | InterruptedException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private HashSet<Package> generatePackage() {
        HashSet<Package> packages = new HashSet();
        
        while(!this.systems.isEmpty()) {
            for(Iterator<String> iterator = this.systems.iterator(); iterator.hasNext();) {
                String systemId = iterator.next();
                HashSet<AbstractCheksAnalyser.AnalyserType> typeToAnalyse = new HashSet();
                String sysId = systemId.split("\\.")[0];
                this.types.stream().forEach((type) -> {            
                    try {
                        if(this.shouldAnalyse(type, sysId)) {
                            typeToAnalyse.add(type);
                        }
                    } catch (Exception ex) {
                    }
                });
                packages.add(new Package(typeToAnalyse, systemId));  
                iterator.remove();
            }
        }        
        return packages;
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
    
    public static void main(String... args) {
        HashSet<AbstractCheksAnalyser.AnalyserType> types = new HashSet();
        
        types.add(AbstractCheksAnalyser.AnalyserType.BOOLEANS);
        types.add(AbstractCheksAnalyser.AnalyserType.BYTESPERBYTES);
        types.add(AnalyserType.BUTTERFLY);
        types.add(AnalyserType.OCCURENCE);
        types.add(AnalyserType.VARIATION);
        types.add(AbstractCheksAnalyser.AnalyserType.NIST_1);
        types.add(AbstractCheksAnalyser.AnalyserType.NIST_2);
        types.add(AbstractCheksAnalyser.AnalyserType.NIST_3);
        //types.add(AnalyserType.NIST_4);
        int port = Integer.parseInt(args[0]);
        new MyServer(types, port);
    }

    @Override
    public Package getAnalyser() {
        
        if(this.packagesToSend.isEmpty()) {
            System.out.println("All system have been sent. Waiting to receive all result.");
            return null;
        }
        Iterator<Package> iterator = this.packagesToSend.iterator();
        Package packToSend = iterator.next();
        iterator.remove();
        
        System.out.println("Sending system: " + packToSend.systemId + " with analyse: " + packToSend.types.size());

        this.analyserGiven++;
        if(this.analyserGiven % 100 == 0) {
            System.out.println("##################################");
            System.out.println("      " + this.analyserGiven + " given!!!    ");
            System.out.println("##################################");
        }
               
        return packToSend;        
    }
    
    private boolean shouldAnalyse(AnalyserType type, String systemId) throws Exception {
        switch (type) {
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
        }
        return false;
    }

    @Override
    public void saveAnalyser(AbstractCheksAnalyser analyser) {
        analyser.saveResult(saver);
    } 
}

