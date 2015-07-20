package cheksAnalyse.butterfly;


import com.archosResearch.jCHEKS.chaoticSystem.Agent;
import com.archosResearch.jCHEKS.chaoticSystem.CryptoChaoticSystem;
import com.archosResearch.jCHEKS.chaoticSystem.exception.KeyLenghtException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 *
 * @author etudiant
 */
public class TempChaoticSystem extends CryptoChaoticSystem{

    public TempChaoticSystem(int keyLength, String systemId) throws KeyLenghtException, NoSuchAlgorithmException {
        super(keyLength, systemId);
    }
    
    public TempChaoticSystem() { }

    public void setAgents(HashMap<Integer, Agent> agents) {
        this.agents = agents;
    }
}
