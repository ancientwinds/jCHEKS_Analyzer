package cheksAnalyse;

import com.archosResearch.jCHEKS.chaoticSystem.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.Arrays;
import java.util.HashSet;

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
     * @param ammountOfByte
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
        if(this.getEvolutionCount() % 1000 == 0) {
            if(logEnabled){
                for(int i = 0; i < this.bytesSaw.length; i++) {
                    System.out.print(this.bytesSaw[i].size() + ",");
                }
                System.out.print("\n");
                System.out.println("Bytes saw: " + Arrays.toString(this.bytesSaw));
            }
        }
    }
}
