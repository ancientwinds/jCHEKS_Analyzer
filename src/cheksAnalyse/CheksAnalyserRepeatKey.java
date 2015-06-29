package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;


public class CheksAnalyserRepeatKey extends CheksAnalyserRepeatKeyIV {

    public CheksAnalyserRepeatKey(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
    }

    @Override
    protected void scan(){
        final StringBuilder builder = new StringBuilder();
        for(byte b : this.getKey()) {
            builder.append(String.format("%02x", b));
        }
        this.keys.add(builder.toString());
    }
}
