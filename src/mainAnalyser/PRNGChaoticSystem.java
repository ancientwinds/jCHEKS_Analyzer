package mainAnalyser;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.exception.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca

 */
public class PRNGChaoticSystem extends AbstractChaoticSystem {
    
    private String fileName;
    private int keyCount = 0;
    private int keyPerLine = 100;
    
    private static  int HEADER_LENGTH = 5;
    
    private List<byte[]> keys;
    
    boolean isNistData;
    
    public PRNGChaoticSystem(String fileName) throws IOException {
        this.isNistData = false;
        this.init(fileName);
    }
    
    public PRNGChaoticSystem(String fileName, boolean nistData) throws IOException {
        this.isNistData = nistData;
        this.init(fileName);
 
    }
    
    private void init(String fileName) throws IOException {
        this.fileName = fileName;
        this.systemId = fileName;
        if(this.isNistData) {
            this.keyPerLine = 10;
            HEADER_LENGTH = 0;
        }
        this.loadKey();
        this.lastGeneratedKey = this.keys.get(0);
    }
    
    @Override
    public int getAgentsCount() {
        return this.lastGeneratedKey.length;
    }
    
    @Override
    public byte[] getKey() throws ChaoticSystemException {
        if(this.keyCount < 1000000) {
            return this.lastGeneratedKey;
        } else {
            throw new NoKeyAvailableException("No more key");
        }
    }
    
    @Override
    public void evolveSystem(int factor) {        
        this.keyCount++;

        if(this.keyCount % keyPerLine == 0 && this.keyCount != 0) {
            try {
                this.loadKey();
            } catch (IOException ex) {
                Logger.getLogger(PRNGChaoticSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.lastGeneratedKey = this.keys.get(keyCount - (keyPerLine *(keyCount / keyPerLine)));       
    }
    
    private void loadKey() throws FileNotFoundException, IOException {        
        FileInputStream fstream = new FileInputStream(this.fileName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
            String line;
            int count = 0;
            
            while ((line = br.readLine()) != null)   {
                if(HEADER_LENGTH + (keyCount / keyPerLine) == count) {
                    if(this.isNistData) {
                        this.readNistKeys(line);
                    } else {
                        this.readKeys(line);
                    }
                    break;
                }
                count++;
            }
        }
        
        this.keys.get(keyCount - (keyPerLine *(keyCount / keyPerLine)));
    }
    
    private void readNistKeys(String line) {
        this.keys = new ArrayList();

        int length = (line.length() / keyPerLine);
        for(int i = 0; i < keyPerLine; i++) {
            String key = line.substring(i * length, length + (i * length)); 
            
            boolean bits[] = new boolean[256];
            for(int j = 0; j < key.length(); j++) {
                bits[j] = (key.charAt(j) == '1');
            }
            
            byte[] toReturn = new byte[bits.length / 8];
            for (int entry = 0; entry < toReturn.length; entry++) {
                for (int bit = 0; bit < 8; bit++) {
                    if (bits[entry * 8 + bit]) {
                        toReturn[entry] |= (128 >> bit);
                    }
                }
            }
            this.keys.add(toReturn);
        }
    }
    
    private void readKeys(String line) {
        this.keys = new ArrayList();

        int length = (line.length() / keyPerLine);
        for(int i = 0; i < keyPerLine; i++) {
            String key = line.substring(i * length, length + (i * length));            
            this.keys.add(Base64.getDecoder().decode(key));
        }
    }

    @Override
    public byte[] getKey(int requiredLength) throws ChaoticSystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetSystem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractChaoticSystem cloneSystem() throws ChaoticSystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String serialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSameState(AbstractChaoticSystem system) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deserialize(String serialization) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void generateSystem(int keyLength, Random random) throws ChaoticSystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}
