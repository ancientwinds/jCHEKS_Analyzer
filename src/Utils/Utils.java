package Utils;

import java.util.Arrays;
import java.util.BitSet;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Utils {
    
    public static boolean[] bytesToBooleanArray(byte[] bytes) {
        boolean[] booleans = new boolean[bytes.length*8];
        for(int i = 0; i < bytes.length; i++){
            System.arraycopy(byteToBooleanArray(bytes[i]), 0, booleans, i*8, 8);
        }
        return booleans;
    }
    
    public static boolean[] byteToBooleanArray(byte aByte) {
        String byteString = String.format("%8s", Integer.toBinaryString(aByte & 0xFF)).replace(' ', '0');
        String[] bitStrings = byteString.split("");
        boolean[] booleanArray = new boolean[8];
        for (int i = 0; i < 8; i++) {
            booleanArray[i] = !bitStrings[i].equals("0");
        }
        return booleanArray;
    }
    
    public static byte[] concatByteArrays(byte[] bytes1, byte[] bytes2){
        byte[] concat = new byte[bytes1.length + bytes2.length];
        for (int i = 0; i < concat.length; ++i){
            concat[i] = i < bytes1.length ? bytes1[i] : bytes2[i - bytes1.length];
        }
        return concat;
    }
    public static double getMaximumInArray(double[] array){
        double[] clone = array.clone();
        Arrays.sort(clone);
        return clone[clone.length-1];
    }
    public static double getMinimumInArray(double[] array){
        double[] clone = array.clone();
        Arrays.sort(clone);
        return clone[0];
    }
    public static double getMedianInArray(double[] array){
        double[] clone = array.clone();
        Arrays.sort(clone);
        int half = clone.length/2;
        return (clone.length%2 == 0)?(clone[half]+clone[half-1])/2:clone[half+1];
    }
    
    public static double getSumInArray(double[] array){
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }
    public static double getAverageInArray(double[] array){
        return getSumInArray(array)/array.length;
    }
    public static double getStandartDeviationInArray(double[] array){
        double stdDevSquared = 0;
        double avg = getAverageInArray(array);
        for (int i = 0; i < array.length; i++) {
            stdDevSquared += (array[i] - avg) * (array[i] - avg)/array.length;
        }
        return Math.sqrt(stdDevSquared);
    }
    
    public static BitSet getBitSet(byte b) {
        BitSet bits = new BitSet(8);
        for(int i = 0; i < 8; i++) {
            bits.set(i, (b & 1) == 1);
            b >>= 1;
        }
        return bits;
    }
    
    public static boolean isPair(int number) {
        if(number % 2 == 0)
            return true;
        return false;
    }
}
