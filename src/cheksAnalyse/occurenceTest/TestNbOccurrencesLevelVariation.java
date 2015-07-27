package cheksAnalyse.occurenceTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;

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
            this.lastKey = system.getKey();
        } else {
            this.currentKey = system.getKey();
            for(int i = 0; i < system.getAgentsCount(); i++) {
                this.distributions[i].registerValue(getVariationByAgent(i));
            }
            this.lastKey = currentKey;
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
