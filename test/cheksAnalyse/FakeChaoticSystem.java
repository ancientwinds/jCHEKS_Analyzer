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
    private final ArrayList<byte[]> keyList;

    /**
     *
     * @param keyList
     */
    public FakeChaoticSystem(ArrayList<byte[]> keyList) {
        super(0);
        this.evolution = 0;
        this.keyList = keyList;
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
        return new FakeChaoticSystem(this.keyList);

    }

    @Override
    public String serialize() {
        return String.valueOf(this.evolution);
    }

    @Override
    protected void generateSystem(int keyLength, Random random) throws ChaoticSystemException {
    }

}
