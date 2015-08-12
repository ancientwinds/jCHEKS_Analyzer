package rmi.server;

import cheksAnalyse.AbstractCheksAnalyser;
import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.*;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Server;
import rmi.AnalyserPackage;
import rmi.model.Model;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class ServerController extends Server implements IServer{

    private final Model model;
    
    public ServerController(HashSet<AbstractCheksAnalyser.AnalyserType> types) {
        this.model = new Model(types);    

        this.loadPackages();
        this.startServerOnPort(10000);
    }
    
    public final void loadPackages() {
        this.model.loadSystems("system");
        this.model.loadPackages();
        
    }
    
    public final void startServerOnPort(int port) {
        Runnable r = () -> {this.startServer(port);};
        new Thread(r).start();
    }
    
    private void startServer(int port) {
        try {
            CallHandler callHandler = new CallHandler();
            callHandler.registerGlobal(IServer.class, this);
            this.bind(port, callHandler);
            System.out.println("Listening on port: " + port);
            System.out.println("Awaiting client to send analyser...");
            while(true) {
                Thread.sleep(500);
            }
        } catch (LipeRMIException | IOException | InterruptedException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String args[]) {
       
        HashSet<AnalyserType> types = new HashSet();
        /*ypes.add(AnalyserType.BOOLEANS);
        types.add(AnalyserType.BYTESPERBYTES);
        types.add(AnalyserType.BUTTERFLY);
        types.add(AnalyserType.OCCURENCE);
        types.add(AnalyserType.VARIATION);
        types.add(AnalyserType.NIST_1);
        types.add(AnalyserType.NIST_2);
        types.add(AnalyserType.NIST_3);
        //types.add(AnalyserType.NIST_4);  */     
        types.add(AnalyserType.KEY_REPETITION);

        new ServerController(types);
        
    }    
    
    @Override
    public void saveAnalyser(AbstractCheksAnalyser analyser, String systemId, AnalyserType type) {
        this.model.saveAnalyser(analyser, systemId, type);
    }

    @Override
    public AnalyserPackage getAnalyser() {
        return this.model.getNextPackage();
    }

    @Override
    public void analyserPackageDone(AnalyserPackage analyserPackage) {
        this.model.analyserPackageDone(analyserPackage);
    }
}
