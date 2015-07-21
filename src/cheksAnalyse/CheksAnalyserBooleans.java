package cheksAnalyse;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.*;
import mainAnalyser.Saver;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserBooleans extends AbstractCheksAnalyser{

    
    private final boolean[] falsesSaw;
    private final boolean[] truesSaw;
    private final int ammountOfBit;
    
    public CheksAnalyserBooleans(boolean enableLog, AbstractChaoticSystem chaoticSystem, int ammountOfBit) throws Exception{
        super(enableLog, chaoticSystem);
        this.falsesSaw = new boolean[ammountOfBit];
        this.truesSaw = new boolean[ammountOfBit];
        this.ammountOfBit = ammountOfBit;
    }

    
    @Override
    protected void scan() {
        byte[] array = this.getKey();
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
        saver.saveEvolutionCount("KEY_BITS", this.getSystemId(), this.getEvolutionCount());
    }
}
