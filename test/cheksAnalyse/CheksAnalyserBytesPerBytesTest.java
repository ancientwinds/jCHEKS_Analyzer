package cheksAnalyse;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserBytesPerBytesTest {
    
    @Test
    public void testCheksAnalyserBytesPerBytes() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        ArrayList<byte[]> ivs = new ArrayList();
        byte i = -128;
        boolean completed = false;
        while(!completed) {
            System.out.println(i);
            keys.add(new byte[]{i,i,i,i,i,i,i,i,i,i,i,i,i,i,i,i});
            ivs.add(new byte[]{i,i,i,i,i,i,i,i,i,i,i,i,i,i,i,i});
            if(i == 127){
                completed = true;
            }
            i++;
        }
        System.out.println(keys.size());
        CheksAnalyserBytesPerBytes analyser = new CheksAnalyserBytesPerBytes(true, new FakeChaoticSystem(keys, ivs));
        assertEquals(255, analyser.getEvolutionCount());
    }

    @Test
    public void testCheksAnalyserBytesPerBytes2() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        ArrayList<byte[]> ivs = new ArrayList();
        byte i = -128;
        boolean completed = false;
        while(!completed) {
            System.out.println(i);
            keys.add(new byte[]{i,i,i,i,i,i,i,i,i,i,i,i,i,i,i,i});
            ivs.add(new byte[]{i,i,i,i,i,i,i,i,i,i,i,i,i,i,i,i});
            keys.add(new byte[]{i,i,i,i,i,i,i,i,i,i,i,i,i,i,i,i});
            ivs.add(new byte[]{i,i,i,i,i,i,i,i,i,i,i,i,i,i,i,i});
            if(i == 127){
                completed = true;
            }
            i++;
        }
        System.out.println(keys.size());
        CheksAnalyserBytesPerBytes analyser = new CheksAnalyserBytesPerBytes(true, new FakeChaoticSystem(keys, ivs));
        assertEquals(510, analyser.getEvolutionCount());
    }
}
