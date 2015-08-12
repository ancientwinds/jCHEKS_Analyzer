package cheksAnalyse.evolutionTest;

import cheksAnalyse.AbstractCheksAnalyser;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import mainAnalyser.AbstractSaver;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public abstract class AbstractEvolutionTest extends AbstractCheksAnalyser {

    public AbstractEvolutionTest(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
    }

    @Override
    public void saveResult(AbstractSaver saver) {
        saver.saveEvolutionCount(this.getTableName(), this.getSystemId(), this.getEvolutionCount());
    }    
}
