package cheksAnalyse;

import Utils.Utils;
import cheksAnalyse.NIST.NistTest1;
import cheksAnalyse.NIST.NistTest2;
import cheksAnalyse.NIST.NistTest3;
import cheksAnalyse.NIST.NistTest4;
import cheksAnalyse.butterfly.CheksButterflyEffectTest;
import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.Arrays;
import java.util.HashSet;
import mainAnalyser.Saver;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public abstract class AbstractCheksAnalyser {

    public enum AnalyserType {
        BYTESPERBYTES,
        BOOLEANS,
        BUTTERFLY,
        OCCURENCE,
        VARIATION,
        NIST_1,
        NIST_2,
        NIST_3,
        NIST_4,
        NIST_5,
        NIST_6,
        NIST_7,
        NIST_8,
        NIST_9,
        NIST_10,
        NIST_11,
        NIST_12,
        NIST_13,
        NIST_14,
        NIST_15
    }
    
    protected final boolean logEnabled;
    private byte[] key;
    final AbstractChaoticSystem chaoticSystem;
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

    protected abstract void scan(AbstractChaoticSystem system);

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

    public void analyse(AbstractChaoticSystem system) {
        updateKey();
        scan(system);
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
    
    public static HashSet<AbstractCheksAnalyser> createAnalyser(HashSet<AnalyserType> types, AbstractChaoticSystem system) throws Exception {
        HashSet<AbstractCheksAnalyser> analyserList = new HashSet();
        for(AnalyserType type : types) {
            switch (type) {
                case BYTESPERBYTES:
                    analyserList.add(new CheksAnalyserBytesPerBytes(false, system));
                    break;
                case BOOLEANS:
                    analyserList.add(new CheksAnalyserBooleans(false, system));
                    break;
                case BUTTERFLY:
                    analyserList.add(new CheksButterflyEffectTest(false, system)); 
                    break;
                case OCCURENCE:
                    analyserList.add(new CheksAnalyserLevelOccurence(system));
                    break;
                case VARIATION:
                    analyserList.add(new CheksAnalyserLevelVariation(system));
                    break;
                case NIST_1:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_2:
                    analyserList.add(new NistTest2(system));
                    break;
                case NIST_3:
                    analyserList.add(new NistTest3(system));
                    break;
                case NIST_4:
                    analyserList.add(new NistTest4(system));
                    break;
                case NIST_5:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_6:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_7:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_8:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_9:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_10:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_11:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_12:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_13:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_14:
                    analyserList.add(new NistTest1(system));
                    break;
                case NIST_15:
                    analyserList.add(new NistTest1(system));
                    break;                   
            }
        }
        return analyserList;
    }
}
