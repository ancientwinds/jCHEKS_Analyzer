package cheksAnalyse;

import cheksAnalyse.evolutionTest.TestCycle;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestCycleTest {

    @Test
    public void findCycle() throws Exception {

        FakeChaoticSystem sys = new FakeChaoticSystem();
        TestCycle test = new TestCycle(sys);
        int i = 0;
        while (!test.isComplete()) {
            test.analyse(sys);
            if(i==100)sys.setEqualsReturnValue (true);
            i++;
            sys.evolveSystem();
        }
    }

    
}

