package cheksAnalyse;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.Arrays;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public abstract class AbstractCheksAnalyser {

    protected final boolean logEnabled;
    private byte[] keyAndIV;
    private final AbstractChaoticSystem chaoticSystem;
    private int evolutionCount;
    private boolean analyseCompleted;

    public AbstractCheksAnalyser(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        this.logEnabled = enableLog;
        this.chaoticSystem = chaoticSystem;
        this.keyAndIV = Utils.concatByteArrays(this.chaoticSystem.getKey(), this.chaoticSystem.getKey());
    }

    public int getEvolutionCount() {
        return evolutionCount;
    }

    protected void complete() {
        this.analyseCompleted = true;
    }

    protected abstract void scan();

    protected abstract void verify();

    protected byte[] getKeyAndIV() {
        return keyAndIV;
    }

    protected byte[] getKey() throws Exception {
        return this.chaoticSystem.getKey(128);
    }

    protected byte[] getIV() throws Exception {
        return this.chaoticSystem.getKey(128);
    }

    public void updateKey() {
        this.keyAndIV = Utils.concatByteArrays(this.chaoticSystem.getKey(), this.chaoticSystem.getKey());
    }

    public void analyse() {
        updateKey();
        scan();
        log();
        verify();
        incrementEvolutionIfNotCompleted();
    }
    private void incrementEvolutionIfNotCompleted(){
        if (!this.analyseCompleted) {
            evolutionCount++;
        }
    }
    
    protected void log() {
        if (this.logEnabled) {
            System.out.println("\nIteration: " + this.evolutionCount);
            System.out.println(Arrays.toString(this.keyAndIV));
        }
    }

    public boolean isComplete() {
        return this.analyseCompleted;
    }
}
