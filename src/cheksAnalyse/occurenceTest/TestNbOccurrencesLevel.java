package cheksAnalyse.occurenceTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;

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
            this.distributions[i].registerValue(chaoticSystem.getKey()[i]);
        }
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
