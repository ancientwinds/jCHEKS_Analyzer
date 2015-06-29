package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserBytes extends AbstractCheksAnalyser{
    
    HashSet<Byte> bytesSaw;
    private static final int AMMOUNT_OF_BYTES = 32;
    public CheksAnalyserBytes(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception{
        super(enableLog, chaoticSystem);
        this.bytesSaw = new HashSet();
        analyse();
    }
    
    @Override
    protected void scan() {
        byte[] array = this.getKeyAndIV();
        for (int i = 0; i < AMMOUNT_OF_BYTES; i++) {
            this.bytesSaw.add(array[i]);
        }
    }
    
    @Override
    protected void verify(){
        if(this.bytesSaw.size()==256){
            complete();
        }
    }
    
    @Override
    protected void log(){
        super.log();
        System.out.println("Bytes saw: " + Arrays.toString(this.bytesSaw.toArray()));
    }
}
