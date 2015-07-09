package cheksAnalyse;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserBooleansTest {

    /**
     * Test of scan method, of class CheksAnalyserBooleans.
     */
    @Test
    public void testCheksAnalyserBooleans1() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        byte i = -128;
        boolean completed = false;
        while(!completed) {
            System.out.println(i);
            keys.add(new byte[]{i,i,i,i,i,i,i,i,i,i,i,i,i,i,i,i});
            if(i == 127){
                completed = true;
            }
            i++;
        }
        System.out.println(keys.size());
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys);
        CheksAnalyserBooleans analyser = new CheksAnalyserBooleans(true, sys, 128);
        while(!analyser.isComplete()){
            analyser.analyse();
            sys.evolveSystem();
        }
        
        assertEquals(analyser.getEvolutionCount(), 128);
    }
    
    /**
     * Test of scan method, of class CheksAnalyserBooleans.
     */
    @Test
    public void testCheksAnalyserBooleans2() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        System.out.println(keys.size());
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys);
        CheksAnalyserBooleans analyser = new CheksAnalyserBooleans(true, sys, 128);
        while(!analyser.isComplete()){
            analyser.analyse();
            sys.evolveSystem();
        }
        
        assertEquals(1, analyser.getEvolutionCount());
    }
}
