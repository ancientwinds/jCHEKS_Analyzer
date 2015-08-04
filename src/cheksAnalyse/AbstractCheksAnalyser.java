package cheksAnalyse;

import cheksAnalyse.distanceTest.TestDistanceBetweenEvolution;
import cheksAnalyse.occurenceTest.*;
import cheksAnalyse.evolutionTest.*;
import cheksAnalyse.nistTest.*;
import cheksAnalyse.distanceTest.butterflyEffect.TestButterflyEffect;
import cheksAnalyse.evolutionTest.*;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import mainAnalyser.AbstractSaver;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public abstract class AbstractCheksAnalyser implements Serializable{

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
        NIST_15,
        DISTANCE_EVOLUTION,
        CYCLE,
        KEY_REPETITION
    }
    
    protected final boolean logEnabled;
    private byte[] key;
    private int evolutionCount;
    private boolean analyseCompleted;
    protected boolean saved = false;
    private final String systemId;
    protected AnalyserType type;
    
    public AbstractCheksAnalyser(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        this.logEnabled = enableLog;
        this.systemId = chaoticSystem.getSystemId();
    }
    
    public abstract void saveResult(AbstractSaver saver);
    public abstract String getTableName();
    protected abstract void scan(AbstractChaoticSystem system);
    protected abstract void verify();

    public int getEvolutionCount() {
        return evolutionCount;
    }

    protected void complete() {
        this.analyseCompleted = true;
    }    
    
    protected String getSystemId() {
        return this.systemId;
    }
    
    public void analyse(AbstractChaoticSystem system) {
        //updateKey();
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
                    analyserList.add(new TestNbEvolutionsAllAgentLevels(false, system));
                    break;
                case BOOLEANS:
                    analyserList.add(new TestNbEvolutionsAllKeyBits(false, system));
                    break;
                case BUTTERFLY:
                    analyserList.add(new TestButterflyEffect(false, system)); 
                    break;
                case OCCURENCE:
                    analyserList.add(new TestNbOccurrencesLevel(system));
                    break;
                case VARIATION:
                    analyserList.add(new TestNbOccurrencesLevelVariation(false, system));
                    break;
                case NIST_1:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case NIST_2:
                    analyserList.add(new TestFrequencyBlockNIST2(system));
                    break;
                case NIST_3:
                    analyserList.add(new TestRunsNIST3(system));
                    break;
                case NIST_4:
                    analyserList.add(new TestLongestRunNIST4(system));
                    break;
                case NIST_5:
                    analyserList.add(new TestBinaryMatrixRankNIST5(system));
                    break;
                case NIST_6:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case NIST_7:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case NIST_8:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case NIST_9:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case NIST_10:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case NIST_11:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case NIST_12:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case NIST_13:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case NIST_14:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case NIST_15:
                    analyserList.add(new TestFrequencyMonobitNIST1(system));
                    break;
                case DISTANCE_EVOLUTION:
                    analyserList.add(new TestDistanceBetweenEvolution(false, system));
                    break;
                case CYCLE:
                    analyserList.add(new TestCycle(system));
                    break;
                case KEY_REPETITION:
                    analyserList.add(new TestKeyRepetition(system));
                    break;
            }
        }
        return analyserList;
    }
    
    public AnalyserType getType() {
        return this.type;
    }
}
