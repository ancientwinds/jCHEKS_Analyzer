package cheksAnalyse;

import Utils.Utils;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.Arrays;
import mainAnalyser.Saver;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public abstract class AbstractCheksAnalyser {

    protected final boolean logEnabled;
    private byte[] key;
    private final AbstractChaoticSystem chaoticSystem;
    private int evolutionCount;
    private boolean analyseCompleted;
    protected boolean saved = false;

    public AbstractCheksAnalyser(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        this.logEnabled = enableLog;
        this.chaoticSystem = chaoticSystem;
        this.key = Utils.concatByteArrays(this.chaoticSystem.getKey(), this.chaoticSystem.getKey());
    }

    public int getEvolutionCount() {
        return evolutionCount;
    }

    protected void complete() {
        this.analyseCompleted = true;
    }

    protected abstract void scan();

    protected abstract void verify();
    
    protected String getSystemId() {
        return this.chaoticSystem.getSystemId();
    }
    
    public abstract void saveResult(Saver saver);

    protected byte[] getKey() {
        return key;
    }

    public void updateKey() {
        this.key = this.chaoticSystem.getKey();
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
            System.out.println(Arrays.toString(this.key));
        }
    }

    public boolean isComplete() {
        return this.analyseCompleted;
    }
}
