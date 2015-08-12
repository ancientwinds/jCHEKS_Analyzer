package cheksAnalyse.distanceTest;

import Utils.Utils;
import cheksAnalyse.AbstractCheksAnalyser;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public abstract class AbstractDistanceTest extends AbstractCheksAnalyser{

    protected int iteration = 1000;
    
    public AbstractDistanceTest(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
    }

    @Override
    protected void verify() {
        if(this.getEvolutionCount() == this.iteration - 1) {
            this.complete();
        }
    }
    
    public static int getDistance(byte[] firstKey, byte[] secondKey) throws Exception {        
        if(firstKey.length == secondKey.length) {
            int distance = 0;
            for(int i = 0; i < firstKey.length; i++) {
                boolean[] baseBool = Utils.byteToBooleanArray(firstKey[i]);
                boolean[] cloneBool = Utils.byteToBooleanArray(secondKey[i]);
                                
                for(int x = 0; x < baseBool.length; x++)
                {
                    if(baseBool[x] != cloneBool[x]) {
                        distance++;
                    }                    
                }                
            }            
            return distance;
        }
        throw new Exception("Key cannot be compared");
    }
    
}
