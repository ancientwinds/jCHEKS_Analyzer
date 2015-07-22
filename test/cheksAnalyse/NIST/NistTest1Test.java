package cheksAnalyse.NIST;

import cheksAnalyse.FakeChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class NistTest1Test {
    
    
    
    @Test
    public void testCalculateSn() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        NistTest1 instance = new NistTest1(sys, 10);
        
        boolean bits[] = {true, false, true, true, false, true, false, true, false, true};
        assertEquals(2, instance.calculateSn(bits));          
    }
    
    @Test
    public void testCalculateSobs() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        NistTest1 instance = new NistTest1(sys, 10);
         
        boolean bits[] = {true, false, true, true, false, true, false, true, false, true};
        int Sn = instance.calculateSn(bits);
        
        assertEquals(0.632455532, instance.calculateSobs(bits, Sn), 0.00001);
        
    }
    
    @Test
    public void testCalculatePValue() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        NistTest1 instance = new NistTest1(sys, 10);
       
        boolean bits[] = {true, false, true, true, false, true, false, true, false, true};
        int Sn = instance.calculateSn(bits);
        double Sobs = instance.calculateSobs(bits, Sn);
        
        assertEquals(0.5271, instance.calculatePValue(Sobs, Sn), 0.0001);
    }
    
    @Test
    public void testExecuteTestShouldPass() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        NistTest1 instance = new NistTest1(sys, 10);
        
        boolean bits[] = {true, false, true, true, false, true, false, true, false, true};
        
        instance.executeTest(bits);
        
        assertTrue(instance.isPassed());
    }    
}
