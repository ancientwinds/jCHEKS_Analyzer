package cheksAnalyse;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.*;
import mainAnalyser.Saver;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestNbEvolutionsAllKeyBits extends AbstractCheksAnalyser{
    
    private boolean[] falsesSaw;
    private boolean[] truesSaw;
    private int ammountOfBit;
    public static final String TABLE_NAME = "nbEvolutions_AllKeyBits";
    
    public TestNbEvolutionsAllKeyBits(AbstractChaoticSystem chaoticSystem) throws Exception{
        super(false, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }
    
    public TestNbEvolutionsAllKeyBits(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception{
        super(enableLog, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }

    private void initAnalyser(AbstractChaoticSystem chaoticSystem) {
        this.type = AnalyserType.BOOLEANS;
        this.ammountOfBit = chaoticSystem.getAgentsCount() * Byte.BYTES;
        this.falsesSaw = new boolean[this.ammountOfBit];
        this.truesSaw = new boolean[this.ammountOfBit];
    }
    
    @Override
    protected void scan(AbstractChaoticSystem chaoticSystem) {
        byte[] array = chaoticSystem.getKey();
        boolean[] booleans = Utils.bytesToBooleanArray(array);
        for (int i = 0; i < ammountOfBit; i++) {
            if(booleans[i]){
                truesSaw[i] = true;
            }else{
                falsesSaw[i] = true;
            }
        }
    }
    
    @Override
    protected void verify(){
        if(isAllTrue(this.falsesSaw) && isAllTrue(this.truesSaw)){
            complete();
        }
    }
    
    private boolean isAllTrue(boolean[] booleans){
        for (int i = 0; i < booleans.length; i++) {
            if (!booleans[i]) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    protected void log(){
        super.log();
        if(logEnabled){
            System.out.println("Falses: " + Arrays.toString(falsesSaw));
            System.out.println("Trues: " + Arrays.toString(truesSaw));
        }
    }

    @Override
    public void saveResult(Saver saver) {
        saver.saveEvolutionCount(TABLE_NAME, this.getSystemId(), this.getEvolutionCount());
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
