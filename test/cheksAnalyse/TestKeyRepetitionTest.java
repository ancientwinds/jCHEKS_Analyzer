package cheksAnalyse;

import cheksAnalyse.evolutionTest.*;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestKeyRepetitionTest {


    @Test
    public void findCycle() throws Exception {

        
        ArrayList<byte[]> keys = new ArrayList();
        byte i = -128;
        boolean completed = false;
        while(!completed) {
            keys.add(new byte[]{i,i,i,i,i,i,i,i,i,i,i,i,i,i,i,i});
            if(i == 127){
                completed = true;
            }
            i++;
        }
        
        FakeChaoticSystem sys = new FakeChaoticSystem(keys, 256);
        TestKeyRepetition test = new TestKeyRepetition(sys);
        while (!test.isComplete()) {
            test.analyse(sys);
            sys.evolveSystem();
        }
    }

    
}

