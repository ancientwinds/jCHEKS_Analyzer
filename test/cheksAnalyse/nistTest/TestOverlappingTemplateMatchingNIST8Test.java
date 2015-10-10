package cheksAnalyse.nistTest;

import static org.junit.Assert.*;
import org.junit.*;
import utils.TestDataLoader;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestOverlappingTemplateMatchingNIST8Test {
    
   /* 
    *    Check Overlapping Patterns Test
    *    pi              p expected =  0.296897  p computed = 0.296897
    *    e               p expected =  0.110434  p computed = 0.110434
    *    sqrt2           p expected =  0.791982  p computed = 0.791982
    *    sqrt3           p expected =  0.082716  p computed = 0.082716
    */
    
    private AbstractNistTest nistTester;
    private MockNISTSaver saver;
    public static double DOUBLE_PRECISION = 0.00001;
    
    @Before
    public void setup() throws Exception{
        nistTester = new TestOverlappingTemplateMatchingNIST8(new ChaoticSystemStub());
        saver = new MockNISTSaver();
    }
    
    @Test
    public void checkWithPiInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/pi");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.296897, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithEInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/e");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.110434, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt2Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt2");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.791982, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt3Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt3");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.082716, saver.getPValue(), DOUBLE_PRECISION);
    }

}
