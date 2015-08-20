package cheksAnalyse.occurenceTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestNbOccurrencesLevel extends AbstractOccurenceTest{
    
    public static final String TABLE_NAME = "nbOccurrences_level";
    
    public TestNbOccurrencesLevel(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(false, chaoticSystem);
        this.type = AnalyserType.OCCURENCE;
    }    
    
    public TestNbOccurrencesLevel(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
        this.type = AnalyserType.OCCURENCE;
    }

    @Override
    protected void scan(AbstractChaoticSystem chaoticSystem) {
        for(int i = 0; i < chaoticSystem.getAgentsCount(); i++) {            
            try {
                this.distributions[i].registerValue(chaoticSystem.getKey()[i]);
            } catch (ChaoticSystemException ex) {
                complete();
            }
        }
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
