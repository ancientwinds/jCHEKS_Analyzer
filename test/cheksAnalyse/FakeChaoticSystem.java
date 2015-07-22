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

    /**
     *
     * @param keyList
     */
    public FakeChaoticSystem(ArrayList<byte[]> keyList, int agentCount) {
        super(0);
        this.evolution = 0;
        this.keyList = keyList;
        this.agentCount = agentCount;
    }

    @Override
    public void deserialize(String serialization) {
        this.evolution = Integer.valueOf(serialization);
    }
    
    @Override
    public void evolveSystem(int factor) {
        this.evolution++;
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
    public void resetSystem() {
    }

    @Override
    public AbstractChaoticSystem cloneSystem() {
        return new FakeChaoticSystem(this.keyList, this.agentCount);

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

}
