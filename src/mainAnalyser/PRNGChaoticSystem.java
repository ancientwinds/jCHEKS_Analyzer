package mainAnalyser;

import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystem;
import java.io.*;
import java.util.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca

 */
public class PRNGChaoticSystem extends ChaoticSystem{
    
    private final String fileName;
    private int keyCount = 0;
    private final int keyPerLine = 100;
    
    private static final int HEADER_LENGTH = 2;
    
    private List<byte[]> keys;
    
    public PRNGChaoticSystem(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public byte[] getKey(int requiredBitLength){
        if(this.keys == null) {
            try {
                this.loadKey();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return this.keys.get(keyCount - (keyPerLine *(keyCount / keyPerLine)));
    }
    
    @Override
    public void evolveSystem(int factor) {
        if(this.keyCount % keyPerLine == 0) {
            this.keys = null;
        }
        this.keyCount++; 
        
    }
    
    @Override
    public void evolveSystem() {
        this.evolveSystem(0);
    }
    
    private void loadKey() throws FileNotFoundException, IOException {        
        FileInputStream fstream = new FileInputStream(this.fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line;
        int count = 0;

        while ((line = br.readLine()) != null)   {
         // System.out.println(line);
          if(HEADER_LENGTH + (keyCount / keyPerLine) == count) {
              this.readKeys(line);
              break;
          }
          count++;
        }

        br.close();
    }
    
    private void readKeys(String line) {
        this.keys = new ArrayList();

        int keyLength = (line.length() / keyPerLine);
        for(int i = 0; i < keyPerLine; i++) {
            String key = line.substring(i * keyLength, keyLength + (i * keyLength));            
            this.keys.add(Base64.getDecoder().decode(key));
        }
    }
    
}
