package cheksAnalyse.occurenceTest;

import cheksAnalyse.AbstractCheksAnalyser;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.AbstractSaver;
import mainAnalyser.Distribution;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */

public abstract class AbstractOccurenceTest extends AbstractCheksAnalyser{

    protected final int iterations = 1000000;
    protected Distribution distributions[];
        
    public AbstractOccurenceTest(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
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
    public void saveResult(AbstractSaver saver) {
        saver.saveDistributionInTable(this.getSystemId(), this.getTableName(), distributions);
    }

    @Override
    protected void verify() {
        if(this.getEvolutionCount() == this.iterations + 1) {
            this.complete();
        }
    }    
}
