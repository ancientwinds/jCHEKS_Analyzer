package rmi.server;

import cheksAnalyse.AbstractCheksAnalyser;
import rmi.AnalyserPackage;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public interface IServer {   
    public AnalyserPackage getAnalyser();
    public void saveAnalyser(AbstractCheksAnalyser analyser, String systemId, AbstractCheksAnalyser.AnalyserType type);
    public void analyserPackageDone(AnalyserPackage analyserPackage);
}

