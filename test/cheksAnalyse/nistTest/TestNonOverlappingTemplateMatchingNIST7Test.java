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
    *    Check Non Overlapping Patterns Test from python implementation
    *    pi              p expected =  0.165757  p computed = 0.165757
    *    e               p expected =  0.07879   p computed = 0.078790
    *    sqrt2           p expected =  0.569461  p computed = 0.569461
    *    sqrt3           p expected =  0.532235  p computed = 0.532235
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
        assertEquals(0.165757, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithEInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/e");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.07879, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt2Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt2");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.569461, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt3Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt3");
        nistTester.executeTest(loader.getDataAsBooleanArray());
        nistTester.saveResult(saver);
        assertEquals(0.532235, saver.getPValue(), DOUBLE_PRECISION);
    }

}
