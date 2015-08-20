package cheksAnalyse.evolutionTest;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.util.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestNbEvolutionsAllKeyBits extends AbstractEvolutionTest{
    
    private boolean[] falsesSaw;
    private boolean[] truesSaw;
    private int ammountOfBit;
    public static final String TABLE_NAME = "nbEvolutions_AllKeyBits";
    private byte[] currentKey;
    
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
        try {
            byte[] array = chaoticSystem.getKey();
            currentKey = array;
            boolean[] booleans = Utils.bytesToBooleanArray(array);
            for (int i = 0; i < ammountOfBit; i++) {
                if(booleans[i]){
                    truesSaw[i] = true;
                }else{
                    falsesSaw[i] = true;
                }
            }
        } catch (ChaoticSystemException ex) {
            complete();
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
            System.out.println(Arrays.toString(currentKey));
            System.out.println("Falses: " + Arrays.toString(falsesSaw));
            System.out.println("Trues: " + Arrays.toString(truesSaw));
        }
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
