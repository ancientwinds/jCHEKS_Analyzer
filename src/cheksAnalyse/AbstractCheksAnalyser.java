package cheksAnalyse;

import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.Arrays;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public abstract class AbstractCheksAnalyser {

    private boolean logEnabled = false;
    private byte[] keyAndIV;
    private final AbstractChaoticSystem chaoticSystem;
    private int evolutionCount;
    private boolean analyseCompleted;

    public AbstractCheksAnalyser(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        this.logEnabled = enableLog;
        this.chaoticSystem = chaoticSystem;
        this.keyAndIV = Utils.concatByteArrays(this.chaoticSystem.getKey(), this.chaoticSystem.getIV());
    }

    public int getEvolutionCount() {
        return evolutionCount;
    }
    
    protected void complete(){
        this.analyseCompleted = true;
    }
    
    protected abstract void scan();

    protected abstract void verify();

    
    protected byte[] getKeyAndIV() {
        return keyAndIV;
    }
    
    protected byte[] getKey() {
        return this.chaoticSystem.getKey();
    }
    
    protected byte[] getIV() {
        return this.chaoticSystem.getIV();
    }

    protected void update() {
        chaoticSystem.evolveSystem();
        this.keyAndIV = Utils.concatByteArrays(this.chaoticSystem.getKey(), this.chaoticSystem.getIV());
        evolutionCount++;
    }

    protected void analyse() {
        while (!this.analyseCompleted) {
            scan();
            if (this.logEnabled) {
                log();
            }
            verify();
            if(!this.analyseCompleted){
                update();
            }
        }
        System.out.println("Iterations: " + this.evolutionCount);
    }
    
    protected void log() {
        System.out.println("\nIteration: " + this.evolutionCount);
        System.out.println(Arrays.toString(this.keyAndIV));
    }

    
}
