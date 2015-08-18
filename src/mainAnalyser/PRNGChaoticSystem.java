package mainAnalyser;

import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.exception.ChaoticSystemException;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca

 */
public class PRNGChaoticSystem extends AbstractChaoticSystem {
    
    private final String fileName;
    private int keyCount = 0;
    private final int keyPerLine = 100;
    
    private static final int HEADER_LENGTH = 2;
    
    private List<byte[]> keys;
    
    public PRNGChaoticSystem(String fileName) throws IOException {
        this.fileName = fileName;
        this.loadKey();
        this.lastGeneratedKey = this.keys.get(0);
    }
    
    @Override
    public int getAgentsCount() {
        return this.lastGeneratedKey.length;
    }
    
    @Override
    public void evolveSystem(int factor) {
        if(this.keyCount % keyPerLine == 0 && this.keyCount != 0) {
            try {
                this.loadKey();
            } catch (IOException ex) {
                Logger.getLogger(PRNGChaoticSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.keyCount++; 
        this.lastGeneratedKey = this.keys.get(keyCount - (keyPerLine *(keyCount / keyPerLine)));
    }
    
    @Override
    public void evolveSystem() {
        this.evolveSystem(0);
    }
    
    private void loadKey() throws FileNotFoundException, IOException {        
        FileInputStream fstream = new FileInputStream(this.fileName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
            String line;
            int count = 0;
            
            while ((line = br.readLine()) != null)   {
                if(HEADER_LENGTH + (keyCount / keyPerLine) == count) {
                    this.readKeys(line);
                    break;
                }
                count++;
            }
        }
        
        this.keys.get(keyCount - (keyPerLine *(keyCount / keyPerLine)));
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
    
    public static void main(String[] args) throws IOException {
        PRNGChaoticSystem c = new PRNGChaoticSystem("PRNGSystem/mt19937_0.txt");
        
        for(int i = 0; i <= 201; i++) {
            System.out.println(i);
            System.out.println(Arrays.toString(c.getKey()));
            c.evolveSystem();
            
        }
    }
    
}
