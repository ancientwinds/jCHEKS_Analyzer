package cheksAnalyse.nistTest;

import cheksAnalyse.FakeChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class TestApproximateEntropyNIST12Test {    
    
    private final TestApproximateEntropyNIST12 instance;
    
    public TestApproximateEntropyNIST12Test() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        instance = new TestApproximateEntropyNIST12(sys, 10, 3);       
    }
    /**
     * Test of executeTest method, of class TestApproximateEntropyNIST12.
     */
    @Test
    public void testExecuteTest() {
        boolean bits[] = new boolean[10];

        String bitsString = "0100110101";
        for(int i = 0; i < bitsString.length(); i++) {
            bits[i] = bitsString.substring(i, i + 1).equals("1");
        }
        
        instance.executeTest(bits);
        
        assertTrue(instance.isPassed());
    }

    public void assertBooleanArray(boolean[] array1, boolean[] array2) {
        assertEquals(array1.length, array2.length);
        
        for(int i = 0; i < array1.length; i++) {
            assertEquals(array1[i], array2[i]);
        }
    }
    
    
}
