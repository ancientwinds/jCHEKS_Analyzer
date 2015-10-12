package cheksAnalyse.nistTest;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.*;
import utils.TestDataLoader;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestLongestRunNIST4Test {
    
   /* 
    *    Testing Longest Run Test  - Results from C implementation (sts-2.1.2)
    *    pi              p expected =  0.706760 (1004882 bits)
    *    e               p expected =  0.675270 (1004882 bits)
    *    sqrt2           p expected =  0.028628(1004883 bits)
    *    sqrt3           p expected =  0.006781 (1004883 bits)
    */
    
    private AbstractNistTest nistTester;
    private MockNISTSaver saver;
    public static double DOUBLE_PRECISION = 0.000001;
    
    @Before
    public void setup() throws Exception{
        nistTester = new TestLongestRunNIST4(new ChaoticSystemStub());
        saver = new MockNISTSaver();
    }
    
    @Test
    public void checkWithPiInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/pi");
        nistTester.executeTest(adjustArraySize(loader.getDataAsBooleanArray()));
        nistTester.saveResult(saver);
        assertEquals(0.706760, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithEInput() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/e");
        nistTester.executeTest(adjustArraySize(loader.getDataAsBooleanArray()));
        nistTester.saveResult(saver);
        assertEquals(0.675270, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt2Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt2");
        nistTester.executeTest(adjustArraySize(loader.getDataAsBooleanArray()));
        nistTester.saveResult(saver);
        assertEquals(0.028628, saver.getPValue(), DOUBLE_PRECISION);
    }
    
    @Test
    public void checkWithSqrt3Input() throws Exception {
        TestDataLoader loader = new TestDataLoader("TestData/sqrt3");
        nistTester.executeTest(adjustArraySize(loader.getDataAsBooleanArray()));
        nistTester.saveResult(saver);
        assertEquals(0.006781, saver.getPValue(), DOUBLE_PRECISION);
    }

    private boolean[] adjustArraySize(boolean[] baseArray){
        return  Arrays.copyOfRange(baseArray, 0, 6272);
    }
}
