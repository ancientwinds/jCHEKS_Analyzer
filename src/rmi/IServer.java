package rmi;

import cheksAnalyse.AbstractCheksAnalyser;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public interface IServer {   
    public Package getAnalyser();
    public void saveAnalyser(AbstractCheksAnalyser analyser);
}
