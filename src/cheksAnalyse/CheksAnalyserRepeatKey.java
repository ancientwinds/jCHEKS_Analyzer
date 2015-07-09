package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.*;

/**
 * @deprecated 
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserRepeatKey extends AbstractCheksAnalyser{

    protected final HashSet<String> keys;
    private int lastSize = 0;
    
    public CheksAnalyserRepeatKey(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
        this.keys = new HashSet();
    }

    @Override
    protected void scan() {
        final StringBuilder builder = new StringBuilder();
        for(byte b : this.getKeyAndIV()) {
            builder.append(String.format("%02x", b));
        }
        this.keys.add(builder.toString());
    }

    @Override
    protected void verify() {
        int currentSize = this.keys.size();
        if(currentSize == this.lastSize){
            this.complete();
        }else{
            this.lastSize = currentSize;
        }
    }
    @Override
    protected void log(){
        if(this.getEvolutionCount()%100000==0){
            super.log();
        }
    }
}
