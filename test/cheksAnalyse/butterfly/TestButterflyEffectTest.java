/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheksAnalyse.butterfly;

import cheksAnalyse.distanceTest.butterflyEffect.TestButterflyEffect;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author etudiant
 */
public class TestButterflyEffectTest {
    
    @Test
    public void testGetDistance() throws Exception {
        byte[] key1 = {1};
        byte[] key2 = {1};
        
        assertEquals(0, TestButterflyEffect.getDistance(key1, key2));
    }
    
    @Test
    public void testGetDistance2() throws Exception {
        byte[] key1 = {1};
        byte[] key2 = {2};
        
        assertEquals(2, TestButterflyEffect.getDistance(key1, key2));
    }
    
    @Test
    public void testGetDistance3() throws Exception {
        byte[] key1 = {1, 1};
        byte[] key2 = {2, 4};
        
        assertEquals(4, TestButterflyEffect.getDistance(key1, key2));
    }

    
}
