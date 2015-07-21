package cheksAnalyse;

import cheksAnalyse.NIST.NistTest1;
import cheksAnalyse.NIST.NistTest3;
import cheksAnalyse.NIST.NistTest2;
import cheksAnalyse.NIST.AbstractNistTest;
import cheksAnalyse.butterfly.*;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.HashMap;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class CheckNistAnalyser extends AbstractCheksAnalyser{

    private HashMap<Integer, AbstractNistTest> nistTests = new HashMap();
    
    public CheckNistAnalyser(boolean enableLog, AbstractChaoticSystem chaoticSystem) throws Exception {
        super(enableLog, chaoticSystem);
        this.nistTests.put(1, new NistTest1());
        this.nistTests.put(2, new NistTest2());
        this.nistTests.put(3, new NistTest3());
        this.nistTests.put(4, new NistTest1());
    }

    @Override
    protected void scan() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void verify() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
