package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserBytes extends AbstractCheksAnalyser{
    
    HashSet<Byte> bytesSaw;
    private final int ammountOfBytes;
    public CheksAnalyserBytes(boolean enableLog, AbstractChaoticSystem chaoticSystem, int ammountOfBytes) throws Exception{
        super(enableLog, chaoticSystem);
        this.ammountOfBytes = ammountOfBytes;
        this.bytesSaw = new HashSet();
    }
    
    @Override
    protected void scan() {
        byte[] array = this.getKey();
        for (int i = 0; i < ammountOfBytes; i++) {
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
