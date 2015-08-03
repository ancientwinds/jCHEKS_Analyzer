package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.util.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class FakeChaoticSystem extends AbstractChaoticSystem {

    private int evolution;
    private int agentCount = 0;
    private final ArrayList<byte[]> keyList;
    private boolean equalsReturnValue;

    /**
     *
     * @param keyList
     */
    public FakeChaoticSystem(ArrayList<byte[]> keyList, int agentCount) {
        super(0);
        this.evolution = 0;
        this.keyList = keyList;
        this.agentCount = agentCount;
        this.equalsReturnValue = false;
    }
    
    public FakeChaoticSystem() {
        super(0);
        keyList = null;
        this.evolution = 0;
    }

    @Override
    public void deserialize(String serialization) {
        this.evolution = Integer.valueOf(serialization);
    }
    
    @Override
    public void evolveSystem(int factor) {
        this.evolution++;
        if(this.evolution > this.keyList.size()) this.evolution = 0;
    }

    @Override
    public byte[] getKey() {
        try {
            return this.keyList.get(this.evolution);
        } catch (IndexOutOfBoundsException ex) {
            return this.keyList.get(0);
        }
    }

    @Override
    public byte[] getKey(int requiredLength) {
        try {
            return this.keyList.get(this.evolution);
        } catch (IndexOutOfBoundsException ex) {
            return this.keyList.get(0);
        }
    }

    @Override
    public boolean isSameState(AbstractChaoticSystem system) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetSystem() {
    }

    @Override
    public AbstractChaoticSystem cloneSystem() {
        FakeChaoticSystem clone = new FakeChaoticSystem(this.keyList, this.agentCount);
        clone.setEvolution(this.evolution);
        return clone;
    }

    @Override
    public String serialize() {
        return String.valueOf(this.evolution);
    }

    @Override
    protected void generateSystem(int keyLength, Random random) throws ChaoticSystemException {
    }
    
    @Override
    public int getAgentsCount() { return this.agentCount;}
    
    public void setEvolution(int evolution){
        this.evolution = evolution;
    }
    
    @Override
    public boolean equals(Object obj){
        return equalsReturnValue;
    }
    
    public void setEqualsReturnValue(boolean value){
        equalsReturnValue = value;
    }
}
