package batchCheksAnalyser;

import cheksAnalyse.AbstractCheksAnalyser;
import cheksAnalyse.CheksAnalyserBooleans;
import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystem;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class BatchAnalyser {

    public BatchAnalyser(int iterations) {
        double[] values = new double[iterations];
        for (int i = 0; i < iterations; i++) {
            try {
                ChaoticSystem system = new ChaoticSystem(256);
                AbstractCheksAnalyser analyser = new CheksAnalyserBooleans(false, system);
                values[i] = analyser.getEvolutionCount();
            } catch (Exception ex) {
                Logger.getLogger(BatchAnalyser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Arrays.sort(values);
        double sum = 0;
        double max = 0;
        double min = Integer.MAX_VALUE;
        for (int i = 0; i < iterations; i++) {
            double value = values[i];
            sum += value;
            max = (max>value) ? max:value;
            min = (min<value) ? min:value;
        }
        double avg = sum / iterations;
        
        double stdDevSquared = 0;
        for (int i = 0; i < iterations; i++) {
            double value = values[i];
            stdDevSquared += (value - avg) * (value - avg)/iterations;
        } 
        double stdDev = Math.sqrt(stdDevSquared);
        int half = iterations/2;
        double median = (iterations % 2 == 0)?(values[half] + values[half-1]) / 2:values[half + 1];
        
        System.out.println("|-----------STATS-----------|");
        System.out.println("| Sum: " + sum);
        System.out.println("| Average: " + avg);
        System.out.println("| Minimum: " + min);
        System.out.println("| Maximum: " + max);
        System.out.println("| Median: " + median);
        System.out.println("| Standart deviation: " + stdDev);
        System.out.println("|---------------------------|");
    }
}
