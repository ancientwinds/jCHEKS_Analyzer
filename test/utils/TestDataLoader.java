package utils;

import java.io.IOException;
import java.nio.file.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class TestDataLoader {
    private String dataAsString;
    
    public TestDataLoader(String pathAsString) throws IOException{
        StringBuilder builder = new StringBuilder();
        for (String line : Files.readAllLines(Paths.get(pathAsString))) {
            builder.append(line.trim());
        }
        this.dataAsString = builder.toString();
    }
    
    public String getDataAsString(){
        return dataAsString;
    }
    
    public boolean[] getDataAsBooleanArray(){
        boolean[] boolArray = new boolean[dataAsString.length()];
        
        for (int i = 0; i < boolArray.length; i++){
            boolArray[i] = dataAsString.charAt(i) == '1';
        }
        return boolArray;
    }
    
}
