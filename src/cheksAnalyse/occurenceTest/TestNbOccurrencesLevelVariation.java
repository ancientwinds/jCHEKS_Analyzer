package cheksAnalyse.occurenceTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestNbOccurrencesLevelVariation extends AbstractOccurenceTest{

    private byte[] lastKey;
    private byte[] currentKey;
    public static final String TABLE_NAME = "nbOccurrences_levelVariation";
    
    public TestNbOccurrencesLevelVariation(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(false, chaoticSystem);
        this.type = AnalyserType.VARIATION;
    }

    public TestNbOccurrencesLevelVariation(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
        this.type = AnalyserType.VARIATION;
    }
    
    @Override
    protected void scan(AbstractChaoticSystem system) {
        if(this.lastKey == null) {
            try {
                this.lastKey = system.getKey();
            } catch (ChaoticSystemException ex) {
                Logger.getLogger(TestNbOccurrencesLevelVariation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                this.currentKey = system.getKey();
                for(int i = 0; i < system.getAgentsCount(); i++) {
                    this.distributions[i].registerValue(getVariationByAgent(i));
                }
                this.lastKey = currentKey;
            } catch (ChaoticSystemException ex) {
                Logger.getLogger(TestNbOccurrencesLevelVariation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }    
      
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }  
    
    private int getVariationByAgent(int agent) {
        int currentLevel = currentKey[agent] + 128;
        int lastLevel = lastKey[agent] + 128;
        int variation = currentLevel - lastLevel;
        if (variation < -128 || variation > 127) {
            return (256 - Math.abs(variation)) * ((variation < 0) ? 1 : -1);
        } else {
            return variation;
        }
    }
    
    
}
