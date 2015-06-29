package cheksAnalyse;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
class Utils {
    
    static boolean[] bytesToBooleanArray(byte[] bytes) {
        boolean[] booleans = new boolean[bytes.length*8];
        for(int i = 0; i < bytes.length; i++){
            System.arraycopy(byteToBooleanArray(bytes[i]), 0, booleans, i*8, 8);
        }
        return booleans;
    }
    
    static boolean[] byteToBooleanArray(byte aByte) {
        String byteString = String.format("%8s", Integer.toBinaryString(aByte & 0xFF)).replace(' ', '0');
        String[] bitStrings = byteString.split("");
        boolean[] booleanArray = new boolean[8];
        for (int i = 0; i < 8; i++) {
            booleanArray[i] = !bitStrings[i].equals("0");
        }
        return booleanArray;
    }
    
    static byte[] concatByteArrays(byte[] bytes1, byte[] bytes2){
        byte[] concat = new byte[bytes1.length + bytes2.length];
        for (int i = 0; i < concat.length; ++i){
            concat[i] = i < bytes1.length ? bytes1[i] : bytes2[i - bytes1.length];
        }
        return concat;
    }
}
