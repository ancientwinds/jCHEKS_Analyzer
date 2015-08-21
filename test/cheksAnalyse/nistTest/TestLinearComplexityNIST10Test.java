/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheksAnalyse.nistTest;

import cheksAnalyse.FakeChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas
 */
public class TestLinearComplexityNIST10Test {
    
    //private final TestLinearComplexityNIST10Test instance;
    
    public TestLinearComplexityNIST10Test() throws Exception {
        /*ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        instance = new TestLinearComplexityNIST10(sys, 10); */       
    }

    /**
     * Test of executeTest method, of class TestLinearComplexityNIST10.
     */
    @Test
    public void testExecuteTest() {
    }

    /**
     * Test of calculateLinearComplixtyForBlocks method, of class TestLinearComplexityNIST10.
     */
    @Test
    public void testCalculateLinearComplixtyForBlocks() {
    }

    /**
     * Test of calculateU method, of class TestLinearComplexityNIST10.
     */
    @Test
    public void testCalculateU() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        TestLinearComplexityNIST10 instance = new TestLinearComplexityNIST10(sys, 1000000, 1000); 
        
        double u = instance.calculateU();
        
        assertEquals(500.22222, u, 0.00001);
    }
    
    @Test
    public void testCalculateVObs() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        TestLinearComplexityNIST10 instance = new TestLinearComplexityNIST10(sys, 1000000, 1000); 
        
        int[] buckets = {11, 31, 116, 501, 258, 57, 26};
        
        double vObs = instance.calculateVobs(buckets);
        
        assertEquals(2.700348, vObs, 0.000001);
    }
    
    @Test
    public void testCalculatePValue() throws Exception {
        ArrayList<byte[]> keys = new ArrayList();
        keys.add(new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});        
        AbstractChaoticSystem sys = new FakeChaoticSystem(keys, 16);        
        TestLinearComplexityNIST10 instance = new TestLinearComplexityNIST10(sys, 1000000, 1000); 
        
        int[] buckets = {11, 31, 116, 501, 258, 57, 26};
        
        double vObs = instance.calculateVobs(buckets);
        double pValue = instance.calculatePValue(vObs);
        
        assertEquals(0.845406, pValue, 0.000001);
    }
       
}
