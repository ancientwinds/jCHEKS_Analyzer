package cheksAnalyse.nistTest;

import static org.junit.Assert.*;
import org.junit.*;
import utils.TestDataLoader;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestSerialNIST11Test {
    
   /* 
    *    Testing Serial Test  - Results from C implementation (sts-2.1.2) (m = 16) Min(P-Value1, P-Value2)
    *    pi              p expected =  0.035510 (1004882 bits)       second p =  0.163817
    *    e               p expected =  0.434688 (1004882 bits)       second p =  0.751935
    *    sqrt2           p expected =  0.659894 (1004883 bits)       second p =  0.870776
    *    sqrt3           p expected =  0.177378 (1004883 bits)       second p =  0.225195
    */
    
    private AbstractNistTest nistTester;
    private MockNISTSaver saver;
    public static double DOUBLE_PRECISION = 0.000001;
    
    @Before
    public void setup() throws Exception{
        nistTester = new TestSerialNIST11(new ChaoticSystemStub());
        saver = new MockNISTSaver();
    }
    
    @Test
    public void checkWithPiInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/pi");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.035510, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithEInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/e");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.434688, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt2Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt2");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.659894, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt3Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt3");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.177378, saver.getPValue(), DOUBLE_PRECISION);
    }

}
