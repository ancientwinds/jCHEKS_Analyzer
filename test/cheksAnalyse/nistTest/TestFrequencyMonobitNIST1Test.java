package cheksAnalyse.nistTest;

import static org.junit.Assert.*;
import org.junit.*;
import utils.TestDataLoader;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestFrequencyMonobitNIST1Test {
    
   /* 
    *    Testing Monobit Test
    *    pi              p expected =  0.613721 (1004882 bits)
    *    e               p expected =  0.926876 (1004882 bits)
    *    sqrt2           p expected =  0.818526 (1004882 bits)
    *    sqrt3           p expected =  0.545494 (1004882 bits)
    */
    
    private AbstractNistTest nistTester;
    private MockNISTSaver saver;
    public static double DOUBLE_PRECISION = 0.00001;
    
    @Before
    public void setup() throws Exception{
        nistTester = new TestFrequencyMonobitNIST1(new ChaoticSystemStub());
        saver = new MockNISTSaver();
    }
    
    @Test
    public void checkWithPiInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/pi");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.613721, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithEInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/e");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.926876, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt2Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt2");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.817751, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt3Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt3");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.546157, saver.getPValue(), DOUBLE_PRECISION);
    }

}
