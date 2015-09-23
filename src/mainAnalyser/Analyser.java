package mainAnalyser;

import cheksAnalyse.AbstractCheksAnalyser;
import cheksAnalyse.AbstractCheksAnalyser.AnalyserType;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class Analyser {
    
    public static void main(String[] args) {
        HashSet<AbstractCheksAnalyser.AnalyserType> types = new HashSet();
        
        /*types.add(AnalyserType.BOOLEANS);
        types.add(AnalyserType.BYTESPERBYTES);
        types.add(AnalyserType.OCCURENCE);
        types.add(AnalyserType.VARIATION);
        */
        types.add(AnalyserType.NIST_9);
        /*types.add(AnalyserType.NIST_2);
        types.add(AnalyserType.NIST_3);
        types.add(AnalyserType.NIST_4);
        types.add(AnalyserType.NIST_5);
        types.add(AnalyserType.NIST_9);
        types.add(AnalyserType.NIST_10);
        types.add(AnalyserType.NIST_12);*/
        //types.add(AnalyserType.DISTANCE_EVOLUTION);
        //types.add(AnalyserType.KEY_REPETITION); 
        
        AbstractMainAnalyser analyser = new MainAnalyser(types);
        //AbstractMainAnalyser analyser = new PRNGAnalyser(types);
        
        try {       
            analyser.analyse();
        } catch (Exception ex) {
            Logger.getLogger(Analyser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
