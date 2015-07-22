package cheksAnalyse.butterfly;

import Utils.Utils;
import cheksAnalyse.AbstractCheksAnalyser;
import com.archosResearch.jCHEKS.chaoticSystem.*;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.*;
import java.util.logging.*;
import mainAnalyser.Saver;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class CheksButterflyEffectTest extends AbstractCheksAnalyser {

    private final HashMap<Integer, CryptoChaoticSystem> clones = new HashMap();
    private final int iteration = 1000;
    private final int[][] distances;
    
    public static final String TABLE_NAME = "butterfly_effect";
    
    public CheksButterflyEffectTest(boolean enableLog, ChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
        this.distances = new int[chaoticSystem.getAgents().size()][this.iteration];               
        this.generateClones(chaoticSystem);        
    }
    
    private void generateClones(AbstractChaoticSystem system) {
        for(int i = 0; i < this.distances.length; i++) {
            TempChaoticSystem clone = new TempChaoticSystem();
            clone.deserialize(system.serialize());
            HashMap<Integer, Agent> agents = clone.getAgents();
            TempAgent agent = new TempAgent(agents.get(i));            
            agent.setKeyPart(Utils.isPair((int)agent.getKeyPart())?(int)agent.getKeyPart() + 1:(int)agent.getKeyPart() - 1);
            agents.replace(i, agent);
            clone.setAgents(agents);
            
            this.clones.put(i, clone);
        }
    }

    @Override
    protected void scan(AbstractChaoticSystem chaoticSystem) {       
        for(int i = 0; i < this.clones.size(); i++) {
            CryptoChaoticSystem clone = this.clones.get(i);
            try {
                this.distances[i][this.getEvolutionCount()] = this.getDistance(this.getKey(), clone.getKey());
            } catch (Exception ex) {
                Logger.getLogger(CheksButterflyEffectTest.class.getName()).log(Level.SEVERE, null, ex);
            }

            clone.evolveSystem();
        }
    }
    
    private int getDistance(byte[] baseKey, byte[] cloneKey) throws Exception {        
        if(baseKey.length == cloneKey.length) {
            int distance = 0;
            for(int i = 0; i < baseKey.length; i++) {
                BitSet baseBits = Utils.getBitSet(baseKey[i]);
                BitSet cloneBits = Utils.getBitSet(cloneKey[i]);
                
                for(int x = 0; x < baseBits.length(); x++)
                {
                    if(baseBits.get(x) != cloneBits.get(x)) {  
                        distance++;
                    }
                }                
            }            
            return distance;
        }
        throw new Exception("Key cannot be compared");
    }

    @Override
    protected void verify() {
        if(this.getEvolutionCount() == this.iteration - 1) {
            this.complete();
        }
    }
    
    public HashMap<Integer, String> getResults() {
        HashMap<Integer, String> results = new HashMap();
        
        for (int i = 0; i < this.distances.length; i ++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");

            for (int j = 0; j < this.distances[i].length; j++) {
                stringBuilder.append(this.distances[i][j]);
                if(j == this.distances[i].length - 1) {
                    stringBuilder.append("]");
                } else {
                    stringBuilder.append(", ");
                }            
            }
            
            results.put(i, stringBuilder.toString());
        }
        
        return results;
    }

    @Override
    public void saveResult(Saver saver) {
        if(!saved) {
            saver.saveButterflyEffect(this.getSystemId(), this.distances);
            saved = true;
        }
    }    
}
