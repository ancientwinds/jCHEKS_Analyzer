package batchCheksAnalyser;

import Utils.Utils;
import cheksAnalyse.AbstractCheksAnalyser;
import cheksAnalyse.CheksAnalyserBooleans;
import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystem;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @deprecated 
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class BatchAnalyserBasic extends AbstractBatchCheksAnalyser {

    private final int iterations;
    private double sum;
    private double avg;
    private double min;
    private double max;
    private double median;
    private double stdDev;
    private Class analyser;

    public BatchAnalyserBasic(int iterations, Class analyser) throws Exception {
        this.iterations = iterations;
        if (analyser.getGenericSuperclass() == AbstractCheksAnalyser.class) {
            this.analyser = analyser;
        } else {
            // TODO Create an exception type.
            throw new Exception("Invalid class in parameters.");
        }
    }

    public void analyse() {
        double[] values = new double[iterations];
        for (int i = 0; i < iterations; i++) {
            try {
                ChaoticSystem system = new ChaoticSystem(256);
                //AbstractCheksAnalyser analyser = new CheksAnalyserBooleans(false, system);
                //analyser.analyse();
                //values[i] = analyser.getEvolutionCount();
            } catch (Exception ex) {
                Logger.getLogger(BatchAnalyserBasic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        sum = Utils.getSumInArray(values);
        max = Utils.getMaximumInArray(values);
        min = Utils.getMinimumInArray(values);
        avg = Utils.getAverageInArray(values);
        stdDev = Utils.getStandartDeviationInArray(values);
        median = Utils.getMedianInArray(values);
    }

    public void displayResult() {
        System.out.println("|-----------STATS-----------|");
        System.out.println("| Sum: " + sum);
        System.out.println("| Average: " + avg);
        System.out.println("| Minimum: " + min);
        System.out.println("| Maximum: " + max);
        System.out.println("| Median: " + median);
        System.out.println("| Standart deviation: " + stdDev);
        System.out.println("|---------------------------|");
    }

    public String[] getStats() {
        return new String[]{String.valueOf(sum), String.valueOf(avg), String.valueOf(min),
            String.valueOf(max), String.valueOf(median), String.valueOf(stdDev)};
    }

    public String[] getTypes() {
        return new String[]{"REAL", "REAL", "REAL", "REAL", "REAL", "REAL"};
    }

    public String[] getNames() {
        return new String[]{"sum", "avg", "min", "max", "median", "stddev"};
    }
}
