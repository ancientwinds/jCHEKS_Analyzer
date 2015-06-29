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
    private final ArrayList<byte[]> ivList;

    /**
     *
     * @param keyList
     * @param ivList
     * @throws java.lang.Exception
     */
    public FakeChaoticSystem(ArrayList<byte[]> keyList, ArrayList<byte[]> ivList) throws Exception {
        super(0);
        this.evolution = 0;
        this.keyList = keyList;
        this.ivList = ivList;
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
    public byte[] getIV() {
        try {
            return this.ivList.get(this.evolution);
        } catch (IndexOutOfBoundsException ex) {
            return this.ivList.get(0);
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
        return new FakeChaoticSystem(this.keyList, this.ivList);
    }

    @Override
    public String Serialize() {
        return null;
    }

    @Override
    public void Deserialize(String serialization) {
    }

    @Override
    protected void generateSystem(int keyLength) throws Exception {
    }

}
