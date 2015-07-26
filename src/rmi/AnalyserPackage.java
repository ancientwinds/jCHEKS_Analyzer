package rmi;

import cheksAnalyse.AbstractCheksAnalyser;
import java.io.Serializable;
import java.util.HashSet;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class AnalyserPackage implements Serializable{
    public HashSet<AbstractCheksAnalyser.AnalyserType> types;
    public String systemId;
    
    public AnalyserPackage(HashSet<AbstractCheksAnalyser.AnalyserType> types, String systemId) {
        this.types = types;
        this.systemId = systemId;
    }
}
