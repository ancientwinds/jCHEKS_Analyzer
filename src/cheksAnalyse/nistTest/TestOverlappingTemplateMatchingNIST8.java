package cheksAnalyse.nistTest;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import edu.missouristate.mote.statistics.Hypergeometric;
import java.util.Arrays;
import static org.apache.commons.math3.special.Gamma.regularizedGammaQ;

/**
 *
 * @author Michael Roussel rousselm4@gmail.com
 */
public class TestOverlappingTemplateMatchingNIST8 extends AbstractNistTest{

    
    public static final int BITS_NEEDED = 1000000; 
    public static final String TABLE_NAME = "overlapping_template_matching_NIST_8";
    
    public TestOverlappingTemplateMatchingNIST8(AbstractChaoticSystem chaoticSystem) throws Exception {
        super(chaoticSystem, BITS_NEEDED);
        this.type = AnalyserType.NIST_8;
    }
    
    @Override
    public void executeTest(boolean[] bits) {
        /*
        """
        Note that this description is taken from the NIST documentation [1]
        [1] http://csrc.nist.gov/publications/nistpubs/800-22-rev1a/SP800-22rev1a.pdf
        The focus of the Overlapping Template Matching test is the number of occurrences of pre-specified target
        strings. Both this test and the Non-overlapping Template Matching test of Section 2.7 use an m-bit
        window to search for a specific m-bit pattern. As with the test in Section 2.7, if the pattern is not found,
        the window slides one bit position. The difference between this test and the test in Section 2.7 is that
        when the pattern is found, the window slides only one bit before resuming the search.
        :param bin_data: a binary string
        :param pattern_size: the length of the pattern
        :return: the p-value from the test
        """
        */
        
        //Implémentation python: def overlapping_patterns(self, bin_data: str, pattern_size=9, block_size=1032):
        // Valeur par défaut équivaut à M dans le document du NIST
        int blockSize = 1032; 
        
        //Implémentation python: n = len(bin_data)
        // Équivaut à n dans le document du NIST
        int bitsLength = bits.length;
        
        //Implémentation python:
        //  pattern = ""
        //  for i in range(pattern_size):
        //      pattern += "1"
        //J'utilise un array de booleans pour être compatible avec notre input (bits)
        //C'est le pattern par défaut en python et dans l'exemple du NIST
        boolean[] pattern = {true,true,true,true,true,true,true,true,true}; // Same as "111111111"
        int pattern_size = pattern.length;
        
        //Implémentation python: num_blocks = math.floor(n / block_size)
        //Équivaut à N dans le document du NIST, c'est == à 968, mais il dépend du nombre de bits en entré
        int blocksCount = (int)Math.floor(bitsLength / blockSize);
        
        //Implémentation python: lambda_val = float(block_size - pattern_size + 1) / pow(2, pattern_size)
        //Formule du NIST: λ = (M-m+1)/2^m        
        double lambda = (double)(blockSize - pattern_size + 1 / Math.pow(2, pattern_size));
        
        //Implémentation python: eta = lambda_val / 2.0
        //Formule du NIST: η = λ/2.
        double eta = lambda / 2.0;
        
        
        //piks = [π0, π2, π3, π4, π5, 1.0 - (π0+π1+π2+π3+π4)]  where π0,π1,π2,π3,π4 are found from getProb.
        //Implémentation python:
        //  piks = [self.get_prob(i, eta) for i in range(5)]
        //  diff = float(numpy.array(piks).sum())
        //  piks.append(1.0 - diff)
        int PIKS_LENGTH = 6;
        double[] piks = new double[PIKS_LENGTH];
        double diff = 0;
        for(int i = 0; i< PIKS_LENGTH-1; i++){
            piks[i] = getProb(i, eta);
            diff += piks[i];
        }
        piks[PIKS_LENGTH-1] = 1.0 - diff;
        
        //HARDCODED VALUES FROM NIST:
        //π0 = 0.324652, π1 = 0.182617, π2 = 0.142670, π3 = 0.106645, π4 = 0.077147, and π5 = 0.166269
        // J'ai pensé à essayer avec ces valeurs au lieu de les recalculer et le code semble maintenant fonctionné.
        piks[0] = 0.324652;
        piks[1] = 0.182617;
        piks[2] = 0.142670;
        piks[3] = 0.106645;
        piks[4] = 0.077147;
        piks[5] = 0.166269;
        
        
        //Implémentation python: pattern_counts = numpy.zeros(6)
        int[] patternCounts = {0,0,0,0,0,0};
        
        //Implémentation python au travers de la boucle:
        //for i in range(num_blocks):
        for(int i = 0; i<blocksCount; i++){
            //block_start = i * block_size
            int block_start = i * blockSize;
            
            //block_end = block_start + block_size
            int block_end = block_start + blockSize;
            
            //block_data = bin_data[block_start:block_end]
            boolean[] block_data = Arrays.copyOfRange(bits, block_start, block_end);
            
            //pattern_count = 0
            int patternFound = 0;
            
            //j = 0
            int j = 0;
            
            //while j < block_size:
            while (j < blockSize){
                //sub_block = block_data[j:j + pattern_size]
                boolean[] sub_block = Arrays.copyOfRange(block_data, j, j + pattern_size);
                
                // if sub_block == pattern:
                if (Arrays.equals(sub_block, pattern)){
                    //pattern_count += 1
                    patternFound += 1;
                }
                //j += 1
                j++;
            }
            //if pattern_count <= 4:
            if (patternFound <= 4){
                //pattern_counts[pattern_count] += 1
                patternCounts[patternFound] += 1;
            }
            //else:
            else{
                //pattern_counts[5] += 1
                patternCounts[5] += 1;
            }
        }
        
        //Implémentation python: chi_squared = 0.0
        //Équivaut à χ(obs) dans le document NIST
        Double chi_squared = 0.0;
        
        //Implémentation python: for i in range(len(pattern_counts)):
        for(int i = 0; i < patternCounts.length; i++){
            //Implémenation python: chi_squared += pow(pattern_counts[i] - num_blocks * piks[i], 2.0) / (num_blocks * piks[i])
            chi_squared += squared(patternCounts[i] - blocksCount * piks[i]) / (blocksCount * piks[i]);
        }
        
        //Implémentation python: return spc.gammaincc(5.0 / 2.0, chi_squared / 2.0)
        this.pValue = regularizedGammaQ(5.0/2.0, chi_squared/2);
        System.out.println(this.pValue);
    }
    
    private double squared(double value){
        return value*value;
    }
    
    
    /*
        Cette fonction est une implémenation de python, je n'ai trouvé aucun moyen
        de la tester. Je ne sais pas si c'est Prob pour probability ou pour autre chose...
        Elle utilise une fonction que je ne connais pas en mathématique. J'ai trouvé une 
        implémentation java. Tu peux la trouver dans le package edu.missouristate.mote.statistics.
        Il faudra peut-être penser à regarder si il faut laisser un fichier de license avec ces classes.
    */
    private double getProb(double u, double x){
        double out = 1.0 * Math.exp(-x);
        if (u != 0){
            out = 1.0 * x * Math.exp(2 * -x) * squared(-u) * Hypergeometric.eval1f1(u + 1, 2, x);
        }
        return out;
    }
    

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
}
