package cheksAnalyse;

import Utils.Utils;
import com.archosResearch.jCHEKS.chaoticSystem.*;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class ButterflyAnalyser extends AbstractCheksAnalyser {

    private final HashMap<Integer, AbstractChaoticSystem> clones = new HashMap();
    private int iteration = 1000;
    private final int[][] distances;
    
    public ButterflyAnalyser(boolean enableLog, ChaoticSystem chaoticSystem, int iteration) throws Exception {
        super(enableLog, chaoticSystem);
        this.iteration = 1000;
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
    protected void scan() {
        byte[] baseKey = this.getKey();
        
        for(int i = 0; i < this.clones.size(); i++) {
            AbstractChaoticSystem clone = this.clones.get(i);
            try {
                this.distances[i][this.getEvolutionCount()] = this.getDistance(baseKey, clone.getKey());
            } catch (Exception ex) {
                Logger.getLogger(ButterflyAnalyser.class.getName()).log(Level.SEVERE, null, ex);
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
            
            for (int[] distance : distances) {
                int total = 0;
                for (int j = 0; j < distance.length; j++) {
                    total += distance[j];
                }
                System.out.println("Average: " + total/1000);
            }
        }
    }
    
}
