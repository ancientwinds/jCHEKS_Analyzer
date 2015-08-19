package cheksAnalyse.evolutionTest;

import cheksAnalyse.AbstractCheksAnalyser;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.util.Arrays;
import java.util.logging.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestKeyRepetition extends AbstractEvolutionTest {


    private AbstractChaoticSystem hare;
    private AbstractChaoticSystem tortoise;
    private int harePosition;
    private int tortoisePosition;
    private int lam;
    private int power;
    private boolean sleep;

    public static final String TABLE_NAME = "nbEvolutions_key_repetition";

    public TestKeyRepetition(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(false, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }

    public TestKeyRepetition(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }

    private void initAnalyser(AbstractChaoticSystem chaoticSystem) throws ChaoticSystemException {
        this.type = AbstractCheksAnalyser.AnalyserType.CYCLE;
        hare = chaoticSystem;
        tortoise = chaoticSystem.cloneSystem();
        tortoisePosition = 0;
        harePosition = 0;
        lam = 0;
        power = 2;
        sleep = true;
    }

    @Override
    protected void scan(AbstractChaoticSystem chaoticSystem) {
        hare = chaoticSystem;
        try {
            if (lam == power) {
                verify();
                lam = 0;
                power *= 2;
                tortoise = hare.cloneSystem();
                tortoisePosition = harePosition;
                System.out.println(harePosition);
                sleep = true;
            }else{
                lam++;
            }
            harePosition++;
            
        } catch (ChaoticSystemException ex) {
            Logger.getLogger(TestCycle.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void verify() {
        if(sleep)sleep = false;
        else try {
            if (hare.getKey() == tortoise.getKey()) {
                System.out.println("CYCLE FOUND");
                System.out.println("STEPS " + lam);
                System.out.println("HARE " + harePosition);
                System.out.println("TORTOISE " + tortoisePosition);
                complete();
            }
        } catch (ChaoticSystemException ex) {
            Logger.getLogger(TestKeyRepetition.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void log() {
        super.log();
        if (logEnabled) {
            try {
                System.out.println("HARE " + Arrays.toString(hare.getKey()));
                System.out.println("TORTOISE " + Arrays.toString(tortoise.getKey()));
            } catch (ChaoticSystemException ex) {
                Logger.getLogger(TestKeyRepetition.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
