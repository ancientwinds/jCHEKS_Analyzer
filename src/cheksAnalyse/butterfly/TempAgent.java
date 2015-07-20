package cheksAnalyse.butterfly;

import com.archosResearch.jCHEKS.chaoticSystem.Agent;

/**
 *
 * @author etudiant
 */
public class TempAgent extends Agent{
    
    public TempAgent(Agent a) {
        this.agentId = a.getAgentId();
        this.keyPart = a.getKeyPart();
        this.keyPartRange = a.getKeyPartRange();
        this.pendingImpacts = a.getPendingImpacts();
        this.ruleSets = a.getRuleSets();
    }
    
    public void setKeyPart(int keyPart) {
        this.keyPart = this.adjustKeyPart(keyPart);
    }
   
    private int adjustKeyPart(int keyPart) {
        if (keyPart > this.keyPartRange.getMax()) {
            keyPart = this.keyPartRange.getMin() + ((keyPart) % 127);
        }
        if (keyPart < this.keyPartRange.getMin()) {
            keyPart = this.keyPartRange.getMax() - ((keyPart * -1) % 128);
        }
        return keyPart;
    }
}
