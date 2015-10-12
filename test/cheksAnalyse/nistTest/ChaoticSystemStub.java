package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.util.Random;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ChaoticSystemStub extends AbstractChaoticSystem{

    @Override
    public void evolveSystem(int factor) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public byte[] getKey(int requiredLength) throws ChaoticSystemException {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void resetSystem() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public AbstractChaoticSystem cloneSystem() throws ChaoticSystemException {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public String serialize() {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public int getAgentsCount() {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public boolean isSameState(AbstractChaoticSystem system) {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public void deserialize(String serialization) {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    protected void generateSystem(int keyLength, Random random) throws ChaoticSystemException {
        throw new UnsupportedOperationException("Not supported."); 
    }
    
}
