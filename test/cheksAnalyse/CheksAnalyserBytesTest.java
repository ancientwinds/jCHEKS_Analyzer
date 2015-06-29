package cheksAnalyse;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserBytesTest {
    

    @Test
    public void testCheksAnalyserBytes() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        ArrayList<byte[]> ivs = new ArrayList();
        byte i = -128;
        boolean completed = false;
        while(!completed) {
            System.out.println(i);
            keys.add(new byte[]{i,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
            ivs.add(new byte[]{i,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
            if(i == 127){
                completed = true;
            }
            i++;
        }
        System.out.println(keys.size());
        CheksAnalyserBytes analyser = new CheksAnalyserBytes(true, new FakeChaoticSystem(keys, ivs));
        assertEquals(255, analyser.getEvolutionCount());
    }
    
    @Test
    public void testCheksAnalyserBytes2() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        ArrayList<byte[]> ivs = new ArrayList();
        byte i = -128;
        boolean completed = false;
        while(!completed) {
            System.out.println(i);
            keys.add(new byte[]{i,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
            ivs.add(new byte[]{i,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
            keys.add(new byte[]{i,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
            ivs.add(new byte[]{i,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
            if(i == 127){
                completed = true;
            }
            i++;
        }
        System.out.println(keys.size());
        CheksAnalyserBytes analyser = new CheksAnalyserBytes(true, new FakeChaoticSystem(keys, ivs));
        assertEquals(510, analyser.getEvolutionCount());
    }
    
}
