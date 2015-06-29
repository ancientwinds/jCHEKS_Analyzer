package cheksAnalyse;

import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserRepeatIVTest {

    @Test
    public void testCheksAnalyserRepeatIV() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        ArrayList<byte[]> ivs = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        System.out.println(keys.size());
        CheksAnalyserRepeatKeyIV analyser = new CheksAnalyserRepeatIV(true, new FakeChaoticSystem(keys, ivs));
        assertEquals(0, analyser.getEvolutionCount());
    }
    
    @Test
    public void testCheksAnalyserRepeatIV2() throws Exception {
        ArrayList<byte[]> ivs = new ArrayList();
        ArrayList<byte[]> keys = new ArrayList();
        ivs.add(new byte[]{0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        System.out.println(ivs.size());
        CheksAnalyserRepeatKeyIV analyser = new CheksAnalyserRepeatKey(true, new FakeChaoticSystem(keys, ivs));
        assertEquals(1, analyser.getEvolutionCount());
    }
    @Test
    public void testCheksAnalyserRepeatIV3() throws Exception {
        ArrayList<byte[]> ivs = new ArrayList();
        ArrayList<byte[]> keys = new ArrayList();
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{0,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        System.out.println(ivs.size());
        CheksAnalyserRepeatKeyIV analyser = new CheksAnalyserRepeatKeyIV(true, new FakeChaoticSystem(keys, ivs));
        assertEquals(8, analyser.getEvolutionCount());
    }
    
}
