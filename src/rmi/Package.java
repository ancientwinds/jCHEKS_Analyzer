package rmi;

import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import java.io.Serializable;
import java.util.HashSet;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class Package implements Serializable{
    public HashSet<AnalyserType> types;
    public String systemId;
    
    public Package(HashSet<AnalyserType> types, String systemId) {
        this.types = types;
        this.systemId = systemId;
    }
}
