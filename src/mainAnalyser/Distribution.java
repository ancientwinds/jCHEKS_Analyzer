package mainAnalyser;

import java.util.Arrays;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Distribution {

    public static int[] getSum(Distribution[] distributions) {
        int[] sum = new int[256];
        for (Distribution distribution : distributions) {
            for (int i = 0; i < distribution.getAgentLevels().length; i++) {
                sum[i] += distribution.getAgentLevels()[i];
            }
        }
        return sum;
    }
    
    private final int[] agentValues;
    
    public Distribution() {
        this.agentValues = new int[256];
    }

    public int[] getAgentLevels() {
        return agentValues;
    }

    public void registerValue(int value) {
        agentValues[adjustLevel(value)]++;
    }

    @Override
    public String toString() {
        return Arrays.toString(agentValues);
    }
    
    private int adjustLevel(int valueToAdjust) {
        return valueToAdjust + Math.abs(Byte.MIN_VALUE);
    }
    
}
