package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserBooleans extends AbstractCheksAnalyser{

    
    private final boolean[] falsesSaw;
    private final boolean[] truesSaw;
    private static final int AMMOUNT_OF_BOOLEANS = 256;
    
    public CheksAnalyserBooleans(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception{
        super(enableLog, chaoticSystem);
        this.falsesSaw = new boolean[AMMOUNT_OF_BOOLEANS];
        this.truesSaw = new boolean[AMMOUNT_OF_BOOLEANS];
        analyse();
    }

    
    @Override
    protected void scan() {
        byte[] array = this.getKeyAndIV();
        boolean[] booleans = Utils.bytesToBooleanArray(array);
        for (int i = 0; i < AMMOUNT_OF_BOOLEANS; i++) {
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
        System.out.println("Falses: " + Arrays.toString(falsesSaw));
        System.out.println("Trues: " + Arrays.toString(truesSaw));
    }
}
