package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.Distribution;
import mainAnalyser.Saver;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestNbOccurrencesLevelVariation extends AbstractCheksAnalyser{

    private final int iterations = 1000000;
    private Distribution distributions[];
    private byte[] lastKey;
    private byte[] currentKey;
    public static final String TABLE_NAME = "nbOccurrences_levelVariation";
    
    public TestNbOccurrencesLevelVariation(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(false, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }

    public TestNbOccurrencesLevelVariation(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }
    
    private void initAnalyser(AbstractChaoticSystem chaoticSystem) {
        this.distributions = new Distribution[chaoticSystem.getAgentsCount()];
        for (int j = 0; j < chaoticSystem.getAgentsCount(); j++) {            
            this.distributions[j] = new Distribution();
        }    
    }

    @Override
    protected void scan(AbstractChaoticSystem system) {
        if(this.lastKey == null) {
            this.lastKey = this.getKey();
        } else {
            this.currentKey = this.getKey();
            for(int i = 0; i < system.getAgentsCount(); i++) {
                this.distributions[i].registerValue(getVariationByAgent(i));
            }
            this.lastKey = currentKey;
        }  
    }

    @Override
    protected void verify() {
        if(this.getEvolutionCount() == this.iterations + 1) {
            this.complete();
        }
    }

    @Override
    public void saveResult(Saver saver) {
        saver.saveDistributionInTable(this.getSystemId(), TABLE_NAME, distributions);
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
