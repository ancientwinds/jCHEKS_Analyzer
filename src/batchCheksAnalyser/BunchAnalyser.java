package batchCheksAnalyser;

import cheksAnalyse.*;
import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class BunchAnalyser {
    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 1000; i++) {
            AbstractChaoticSystem CS = new ChaoticSystem(128);
            AbstractCheksAnalyser booleanAnalyser = new CheksAnalyserBooleans(false, CS);
            AbstractCheksAnalyser bytesAnalyser = new CheksAnalyserBytes(false, CS);
            AbstractCheksAnalyser bytesPerBytesAnalyser = new CheksAnalyserBytesPerBytes(false, CS);
            int min = booleanAnalyser.getEvolutionCount();
            int middle = bytesAnalyser.getEvolutionCount();
            int max = bytesPerBytesAnalyser.getEvolutionCount();
            
            
        }
        
    }
}
