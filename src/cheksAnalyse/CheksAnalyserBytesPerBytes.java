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
    private final int ammountOfByte;

    /**
     *
     * @param enableLog
     * @param chaoticSystem
     * @throws Exception
     */
    public CheksAnalyserBytesPerBytes(boolean enableLog, AbstractChaoticSystem chaoticSystem, int ammountOfByte) throws Exception{
        super(enableLog, chaoticSystem);
        this.ammountOfByte = ammountOfByte;
        this.bytesSaw = new HashSet[ammountOfByte];
        for (int i = 0; i < this.bytesSaw.length; i++) {
            this.bytesSaw[i] = new HashSet();
        }
        
    }
    
    @Override
    protected void scan() {
        byte[] array = this.getKey();
        for (int i = 0; i < ammountOfByte; i++) {
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
        if(logEnabled){
            System.out.println("Bytes saw: " + Arrays.toString(this.bytesSaw));
        }
    }
}
