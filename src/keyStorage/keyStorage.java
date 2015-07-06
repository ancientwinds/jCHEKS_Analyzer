package keyStorage;

import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystem;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class keyStorage {

    public static void main(String args[]) throws Exception {
        try {
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:keys.db");
                System.out.println("Opened database successfully");

                stmt = c.createStatement();
            //String sql = "CREATE TABLE keys (key TEXT PRIMARY KEY)";
                //stmt.executeUpdate(sql);
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Table created successfully");
            AbstractChaoticSystem sys = new ChaoticSystem(128);

            for (int i = 0; i < 1000; i++) {
                String sql = "INSERT INTO keys (key) "
                        + "VALUES (\"" + Arrays.toString(sys.getKey(128)) + "\");";
                sys.evolveSystem();
                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
            //c.commit();

        } catch (SQLException ex) {
            Logger.getLogger(keyStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
