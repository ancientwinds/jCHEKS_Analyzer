package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;

/**
 *
 * @author Michael Roussel rousselm4@gmail.com
 */
public class TestCumulativeSumsNIST13 extends AbstractNistTest{

    public static final int BITS_NEEDED = 0; //TODO Set this value
    public static final String TABLE_NAME = "cumulative_sums_NIST_13";
    
    public TestCumulativeSumsNIST13(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_13;
    }
    
    @Override
    public void executeTest(boolean[] bin_data) {
        /* """
        Note that this description is taken from the NIST documentation [1]
        [1] http://csrc.nist.gov/publications/nistpubs/800-22-rev1a/SP800-22rev1a.pdf
        The focus of this test is the maximal excursion (from zero) of the random walk defined by the cumulative sum of
        adjusted (-1, +1) digits in the sequence. The purpose of the test is to determine whether the cumulative sum of
        the partial sequences occurring in the tested sequence is too large or too small relative to the expected
        behavior of that cumulative sum for random sequences. This cumulative sum may be considered as a random walk.
        For a random sequence, the excursions of the random walk should be near zero. For certain types of non-random
        sequences, the excursions of this random walk from zero will be large.
        :param bin_data: a binary string
        :param method: the method used to calculate the statistic
        :return: the P-value
        """*/
        int n = bin_data.length;
        //n = len(bin_data)
        
        int[] counts = new int[n];
        for(int i = 0; i<n;i++){
            counts[i] = 0;
        }
        //counts = numpy.zeros(n)
        
        
        //Calculate the statistic using a walk forward
        //if method != "forward":
        //    bin_data = bin_data[::-1]

        int ix = 0;
        //ix = 0
        
        for (boolean bool : bin_data) {
            int sub = 1;
            if(!bool){
                sub = -1;
            }
            if (ix>0){
                counts[ix] = counts[ix-1] + sub;
            } else{
                counts[ix] = sub;
            }
            ix++;
        }
        /* for char in bin_data:
            sub = 1
            if char == '0':
                sub = -1
            if ix > 0:
                counts[ix] = counts[ix - 1] + sub
            else:
                counts[ix] = sub
            ix += 1
        */
        
        
        int abs_max = 0;
        for (int count : counts) {
            int absCount = Math.abs(count);
            abs_max = abs_max > absCount ? absCount : abs_max;
        }
        //# This is the maximum absolute level obtained by the sequence
        //abs_max = numpy.max(numpy.abs(counts))
        
        
        int start = (int)(Math.floor(0.25 * Math.floor(-n / abs_max) + 1));
        //start = int(numpy.floor(0.25 * numpy.floor(-n / abs_max) + 1))
        
        int end = (int)(Math.floor(0.25 * Math.floor(-n / abs_max) - 1));
        //end = int(numpy.floor(0.25 * numpy.floor(n / abs_max) - 1))
        
        double[] terms_one = new double[end + 1 - start];
        for (int i = start; i <= end + 1; i++) {
            int sub = 1;//sst.norm.cdf((4 * i - 1) * abs_max / Math.sqrt(n));
            terms_one[i] = 1;//(sst.norm.cdf((4 * i + 1) * abs_max / Math.sqrt(n)) - sub)
        }
        /*
        sst is for scipy.stats so the function is scipy.stats.norm.cdf
        The docs say that: cdf(x, loc=0, scale=1)	Cumulative density function.
        Wikipedia say that:
            Cumulative density function
                From Wikipedia, the free encyclopedia
                Cumulative density function is a self-contradictory phrase resulting from confusion between:
                    probability density function, and
                    cumulative distribution function.
                The two words cumulative and density contradict each other.
        */
        
        
        /*terms_one = []
        for k in range(start, end + 1):
            sub = sst.norm.cdf((4 * k - 1) * abs_max / numpy.sqrt(n))
            terms_one.append(scipy.stats.norm.cdf((4 * k + 1) * abs_max / numpy.sqrt(n)) - sub)
            */
        
        start = (int)(Math.floor(0.25 * Math.floor(-n / abs_max - 3)));
        //start = int(numpy.floor(0.25 * numpy.floor(-n / abs_max - 3)))
        
        end = (int)(Math.floor(0.25 * Math.floor(n / abs_max - 1)));
        //end = int(numpy.floor(0.25 * numpy.floor(n / abs_max) - 1))
        
        double[] terms_two = new double[end + 1 - start];
        //terms_two = []
        
        for (int i = start; i <= end; i++) {
            int sub = 1;//sst.norm.cdf((4 * i + 1) * abs_max / Math.sqrt(n))
            terms_two[i] = 1;//sst.norm.cdf((4 * i + 3) * abs_max / Math.sqrt(n)) - sub)
        }
        /*for k in range(start, end + 1):
            sub = sst.norm.cdf((4 * k + 1) * abs_max / numpy.sqrt(n))
            terms_two.append(sst.norm.cdf((4 * k + 3) * abs_max / numpy.sqrt(n)) - sub)
        */
        
        double sum1 = 0;
        for (double value : terms_one) {
            sum1+=value;
        }
        
        double sum2 = 0;
        for (double value : terms_two) {
            sum2 += value;
        }
        double p_val = 1.0 - sum1;
        p_val += sum2;
        /*p_val = 1.0 - numpy.sum(numpy.array(terms_one))
        p_val += numpy.sum(numpy.array(terms_two))*/
        
        this.pValue = p_val;
        //return p_val
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
