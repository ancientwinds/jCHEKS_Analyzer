package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
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
     * @throws java.lang.Exception
     */
    public FakeChaoticSystem(ArrayList<byte[]> keyList) throws Exception {
        super(0);
        this.evolution = 0;
        this.keyList = keyList;
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
    public byte[] getKey(int requiredLength) throws Exception {
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
    public AbstractChaoticSystem cloneSystem() throws Exception {
        return new FakeChaoticSystem(this.keyList);
    }

    @Override
    public String serialize() {
        return null;
    }

    @Override
    public void Deserialize(String serialization) {
    }

    @Override
    protected void generateSystem(int keyLength) throws Exception {
    }

}
