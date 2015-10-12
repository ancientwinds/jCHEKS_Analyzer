package cheksAnalyse.nistTest;

import static org.junit.Assert.*;
import org.junit.*;
import utils.TestDataLoader;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestNonOverlappingTemplateMatchingNIST7Test {
    

   /* 
    *    Check Non Overlapping Patterns Test - Results from C implementation (sts-2.1.2) For pattern "000000001" (m = 9)
    *    pi              p expected =  0.167042  (1004882 bits)
    *    e               p expected =  0.138094  (1004882 bits)
    *    sqrt2           p expected =  0.560931  (1004883 bits)
    *    sqrt3           p expected =  0.424530  (1004883 bits)
    */
    
    private AbstractNistTest nistTester;
    private MockNISTSaver saver;
    public static double DOUBLE_PRECISION = 0.00001;
    
    @Before
    public void setup() throws Exception{
        nistTester = new TestNonOverlappingTemplateMatchingNIST7(new ChaoticSystemStub());
        saver = new MockNISTSaver();
    }
    
    @Test
    public void checkWithPiInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/pi");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.167042, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithEInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/e");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.138094, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt2Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt2");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.560931, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt3Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt3");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.424530, saver.getPValue(), DOUBLE_PRECISION);
    }

}
