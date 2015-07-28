package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.*;
import mainAnalyser.AbstractSaver;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserBytes extends AbstractCheksAnalyser{
    
    HashSet<Byte> bytesSaw;
    private int ammountOfBytes;
    private static String TABLE_NAME = "";
    
    public CheksAnalyserBytes(AbstractChaoticSystem chaoticSystem) throws Exception{
        super(false, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }
        
    public CheksAnalyserBytes(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception{
        super(enableLog, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }
    
    private void initAnalyser(AbstractChaoticSystem chaoticSystem) {
        this.ammountOfBytes = chaoticSystem.getAgentsCount();
        this.bytesSaw = new HashSet();
    }
    
    @Override
    protected void scan(AbstractChaoticSystem chaoticSystem) {
        byte[] array = chaoticSystem.getKey();
        
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

    @Override
    public void saveResult(AbstractSaver saver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
