package cheksAnalyse.evolutionTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.util.logging.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestCycle extends AbstractEvolutionTest {


    private AbstractChaoticSystem hare;
    private AbstractChaoticSystem tortoise;
    private int harePosition;
    private int tortoisePosition;
    private int lam;
    private int mu;
    private int power;
    private boolean sleep;

    public static final String TABLE_NAME = "nbEvolutions_cycle";

    public TestCycle(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(false, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }

    public TestCycle(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
        this.initAnalyser(chaoticSystem);
    }

    private void initAnalyser(AbstractChaoticSystem chaoticSystem) throws ChaoticSystemException {
        this.type = AnalyserType.CYCLE;
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
                sleep = true;
                System.out.println("\n\nHare: " + harePosition + "\nTortoise: " + tortoisePosition);
            }
            harePosition++;
            lam++;
        } catch (ChaoticSystemException ex) {
            Logger.getLogger(TestCycle.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void verify() {
        if(sleep)sleep = false;
        else if (hare.isSameState(tortoise)) {
            System.out.println("CYCLE FOUND");
            System.out.println("STEPS " + lam);
            System.out.println("HARE " + harePosition);
            System.out.println("TORTOISE " + tortoisePosition);
            complete();
        }
        if(harePosition>1000000){
            System.out.println("HARE " + harePosition);
            complete();
        }
    }

    @Override
    protected void log() {
        super.log();
        if (logEnabled) {

        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
