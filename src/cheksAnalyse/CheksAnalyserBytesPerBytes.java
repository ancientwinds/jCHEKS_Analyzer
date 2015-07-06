package cheksAnalyse;

import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserBytesPerBytes extends AbstractCheksAnalyser{
    
    private HashSet<Byte>[] bytesSaw;
    private static final int AMMOUNT_OF_BYTES = 32;

    /**
     *
     * @param enableLog
     * @param chaoticSystem
     * @throws Exception
     */
    public CheksAnalyserBytesPerBytes(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception{
        super(enableLog, chaoticSystem);
        this.bytesSaw = new HashSet[AMMOUNT_OF_BYTES];
        for (int i = 0; i < this.bytesSaw.length; i++) {
            this.bytesSaw[i] = new HashSet();
        }
    }
    
    @Override
    protected void scan() {
        byte[] array = this.getKeyAndIV();
        for (int i = 0; i < AMMOUNT_OF_BYTES; i++) {
            this.bytesSaw[i].add(array[i]);
        }
    }
    
    @Override
    protected void verify(){
        if(everyBytesSaw()){
            complete();
        }
    }
    
    private boolean everyBytesSaw(){
        for (HashSet<Byte> element : this.bytesSaw) {
            if (element.size() != 256) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    protected void log(){
        super.log();
        System.out.println("Bytes saw: " + Arrays.toString(this.bytesSaw));
    }
}
