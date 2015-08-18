package Utils;

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
    
    public static BitSet getBitSet(byte b) {
        BitSet bits = new BitSet(8);
        for(int i = 0; i < 8; i++) {
            bits.set(i, (b & 1) == 1);
            b >>= 1;
        }
        return bits;
    }
    
    public static boolean isPair(int number) {
        return number % 2 == 0;
    }
    
    public static boolean[][] partitionBitsInBlocks(boolean[] bits, int blockLength) {        
        
        boolean blockBits[][] = new boolean[bits.length/blockLength][blockLength];
        
        for(int i = 0; i < bits.length/blockLength; i++) {
            for(int j = 0; j < blockLength; j++) {
                blockBits[i][j] = bits[j + (i * blockLength)]; 
            }
        }

        return blockBits;
    }    

    public static boolean[][][] createMatrices(boolean[] bits, int rowsMatrix, int columnsMatrix) {
        boolean[][][] matrices =  new boolean[bits.length / (rowsMatrix * columnsMatrix)][rowsMatrix][columnsMatrix];
        
        for(int i = 0; i < bits.length / (rowsMatrix * columnsMatrix); i++) {
            for(int j = 0; j < rowsMatrix; j++) {
                for(int k = 0; k < columnsMatrix; k++) {
                    int pos = (i * rowsMatrix * columnsMatrix) + (j * rowsMatrix) + k;
                    matrices[i][j][k] = bits[pos];
                }
            }
        }
        
        return matrices;
    }
    
    public static boolean[][] prepareMatrix(boolean[][] matrix) {
        
        matrix = Utils.doForwardTransformation(matrix);
        matrix = Utils.doBackwardTransformation(matrix);
        
        return matrix;
    }
    
    public static boolean[][] doForwardTransformation(boolean[][] matrix) {

        for(int i = 0; i < matrix.length; i++) {
            if(i + 1 < matrix.length) {
                if(matrix[i][i] == false) {                    
                    for(int j = i + 1; j < matrix.length; j++) {
                        if(matrix[j][i] == true) {
                            boolean[] current = matrix[i];
                            matrix[i] = matrix[j];
                            matrix[j] = current;
                            break;
                        }
                    }                    
                }
                
                if(matrix[i][i] == true) {
                    for(int k = i + 1; k < matrix.length; k++) {
                        if(matrix[k][i] == true) {
                            matrix[k] = Utils.xoring(matrix[i], matrix[k]);
                        }
                    }
                }
            }
        }
        
        return matrix;
    }
    
    public static boolean[][] doBackwardTransformation(boolean[][] matrix) {
        for(int i = matrix.length - 1; i >= 0; i--) {
            if(i - 1 >= 0) {
                if(matrix[i][i] == false) {
                    for(int j = i -1; j >= 0; j--) {
                        if(matrix[j][i] == true) {
                            boolean[] current = matrix[i];
                            matrix[i] = matrix[j];
                            matrix[j] = current;
                            break;
                        }
                    }
                }
                
                if(matrix[i][i] == true) {
                    for(int k = i - 1; k >= 0; k--) {
                        if(matrix[k][i] == true) {
                            matrix[k] = Utils.xoring(matrix[i], matrix[k]);
                        }
                    }
                }
            }
        }
        return matrix;
    }
    
    public static boolean[] xoring(boolean[] bits, boolean[] bits2) {
        boolean[] result = new boolean[bits.length];
        
        for(int i = 0; i < bits.length; i++) {
            result[i] = bits[i] != bits2[i];
        }
        
        return result;
    }
    
    public static double logBase2(double a) {
        return logB(a, 2);
    }
    
    public static double logB(double a, double b) {
        return Math.log(a) / Math.log(b);
    }  
    
    public static int calculateLinearComplexity(boolean[] block) {
        final int N = block.length;
        int[] b = new int[N];
        int[] c = new int[N];
        int[] t = new int[N];
        
        b[0] = 1;
        c[0] = 1;
        
        int l = 0;
        int m = -1;
        
        for(int n = 0; n < N; n++) {
            int d = 0;
            for(int i = 0; i <= l; i++) {
                d ^= c[i] * (block[n - i]?1:0);
            }
            
            if(d == 1) {
                System.arraycopy(c, 0, t, 0, N);
                int N_M = n - m;
                for(int j = 0; j < N - N_M; j++) {
                    c[N_M + j] ^= b[j];
                }
                if(l <= n / 2) {
                    l = n + 1 - l;
                    m = n;
                    System.arraycopy(t, 0, b, 0, N);
                }
            }
        }
        
        return l;
    }

    public static int convertBooleanArrayToInt(boolean[] booleansArray) {        
        int count = 0;
        for(int i = 0; i < booleansArray.length; i++) {
            if(booleansArray[i]) {
                count += Math.pow(2, (booleansArray.length - 1) - i);
            }
        }
        return count;
    }
    
    
    //TODO Verify if the implementation is correct.
    public static double[] calculateDiscreteFourierTransformation(double[] sequence) {
        int N = sequence.length;
        
        double twoPikOnN;
        double twoPijkOnN;
        
        int n = N >> 1;
        
        double twoPiOnN = 2 * Math.PI / N;
        
        double r_data[] = new double[N];
        double i_data[] = new double[N];
        double psd[] = new double[N];
        
        for(int k = 0; k < N; k++) {
            twoPikOnN = twoPiOnN * k;
            
            for(int j = 0; j < N; j++) {
                twoPijkOnN = twoPikOnN * j;
                r_data[k] += sequence[j] * Math.cos(twoPijkOnN);
                i_data[k] -= sequence[j] * Math.sin(twoPijkOnN);
            }
            
            r_data[k] /= N;
            i_data[k] /= N;
            
            psd[k] = r_data[k] * r_data[k] + i_data[k] * i_data[k];
        }
        
        return psd;
    }
    
    


}
