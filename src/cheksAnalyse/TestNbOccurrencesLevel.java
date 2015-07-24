package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.Distribution;
import mainAnalyser.Saver;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestNbOccurrencesLevel extends AbstractCheksAnalyser{

    private final int iterations = 1000000;
    private Distribution distributions[];
    
    public static final String TABLE_NAME = "nbOccurrences_level";
    
    public TestNbOccurrencesLevel(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(false, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }    
    
    public TestNbOccurrencesLevel(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }
    
    private void initAnalyser(AbstractChaoticSystem chaoticSystem) {
        this.type = AnalyserType.OCCURENCE;
        this.distributions = new Distribution[chaoticSystem.getAgentsCount()];
        for (int j = 0; j < chaoticSystem.getAgentsCount(); j++) {            
            this.distributions[j] = new Distribution();
        }
    }

    @Override
    protected void scan(AbstractChaoticSystem chaoticSystem) {
        for(int i = 0; i < chaoticSystem.getAgentsCount(); i++) {
            
            this.distributions[i].registerValue(chaoticSystem.getKey()[i]);
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
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
