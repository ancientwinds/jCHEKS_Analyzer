/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.client;

import cheksAnalyse.AbstractCheksAnalyser;
import com.archosResearch.jCHEKS.chaoticSystem.FileReader;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.logging.*;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;
import rmi.AnalyserPackage;
import rmi.server.IServer;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class ClientController {
    
    private static final String FILE_TO_STOP = "removeToStop.stop";
    private static int port = 10000;
    
    private IServer serviceCaller;
    public static void main(String... args) throws Exception {
        if(args.length == 0) {
            System.out.println("You need to specify at least an ip adress");
        } else {
            String ipAdress = args[0];
            if(args.length == 1) {
                System.out.println("Using default port: " + port);
            } else {
                port = Integer.parseInt(args[1]);
            }
        
            new ClientController(ipAdress, port);
        }        
    }
    
    public ClientController(String ip, int port) {
        
        File f = new File(FILE_TO_STOP);
        try {
            f.createNewFile();
        } catch (IOException ex) {
            System.out.println("Could not create file to stop analyse. " + ex.getMessage());
        }
        
        try {
            CallHandler callHandler = new CallHandler();
            Client client = new Client(ip, port, callHandler);
            
            this.serviceCaller = client.getGlobal(IServer.class); 
            this.analyse();            
            
            client.close();
        } catch (Exception ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void analyse() throws Exception {
        while(this.shouldContinueAnalyse()) {
            AnalyserPackage p = this.serviceCaller.getAnalyser();
            
            if(p == null) {
                return;
            }
            int count = 0;
            System.out.println("Starting to analyse: " + p.systemId);
            Date startTime = new Date();
            AbstractChaoticSystem currentChaoticSystem = FileReader.readChaoticSystem(p.systemId);
            HashSet<AbstractCheksAnalyser> analysers = AbstractCheksAnalyser.createAnalyser(p.types, currentChaoticSystem);
            while(!analysers.isEmpty()) {                
                for(Iterator<AbstractCheksAnalyser> iterator = analysers.iterator(); iterator.hasNext();) {
                    AbstractCheksAnalyser analyser = iterator.next();

                    if(!analyser.isComplete()) {
                        analyser.analyse(currentChaoticSystem);
                    } else {
                        iterator.remove();
                        this.serviceCaller.saveAnalyser(analyser, p.systemId, analyser.getType());
                        count++;
                    }
                    analyser = null;
                }                
                currentChaoticSystem.evolveSystem();
            }                
            
            this.serviceCaller.analyserPackageDone(p);
            DateFormat dateFormat = new SimpleDateFormat("mm:ss");
            Date date = new Date((new Date()).getTime() - startTime.getTime());
            System.out.println(count + "/" + p.types.size() + " analyses done on " + p.systemId + ". It took: " + dateFormat.format(date));
        }
    }
    
    private boolean shouldContinueAnalyse() {
        File f = new File(FILE_TO_STOP);
        return f.exists() && !f.isDirectory();
    }
}

