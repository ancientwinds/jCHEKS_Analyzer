package cheksAnalyse;

import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class CheksAnalyserRepeatKeyIVTest {

    @Test
    public void testCheksAnalyserRepeatKeyIV() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        ArrayList<byte[]> ivs = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        System.out.println(keys.size());
        CheksAnalyserRepeatKeyIV analyser = new CheksAnalyserRepeatKeyIV(true, new FakeChaoticSystem(keys, ivs));
        assertEquals(1, analyser.getEvolutionCount());
    }
    
    @Test
    public void testCheksAnalyserRepeatKeyIV2() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        ArrayList<byte[]> ivs = new ArrayList();
        keys.add(new byte[]{0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        System.out.println(keys.size());
        CheksAnalyserRepeatKeyIV analyser = new CheksAnalyserRepeatKeyIV(true, new FakeChaoticSystem(keys, ivs));
        assertEquals(2, analyser.getEvolutionCount());
    }
    @Test
    public void testCheksAnalyserRepeatKeyIV3() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        ArrayList<byte[]> ivs = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{0,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        keys.add(new byte[]{0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        ivs.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        System.out.println(keys.size());
        CheksAnalyserRepeatKeyIV analyser = new CheksAnalyserRepeatKeyIV(true, new FakeChaoticSystem(keys, ivs));
        assertEquals(8, analyser.getEvolutionCount());
    }
    
}
