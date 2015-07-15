package mainAnalyser;

import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Saver {

    private Connection connection;
    private Statement statement;
    private final int numberOfAgent;

    public Saver(int numberOfAgent) {
        this.numberOfAgent = numberOfAgent;
        this.connection = null;
        this.statement = null;
    }

    public void initDatabase() {
        openDatabase();
        createEvolutionTable("keybits");
        createEvolutionTable("agentlevels");
        createDistributionTable("variations");
        createDistributionTable("occurences");
    }

    private void openDatabase() {

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:keys.db");
            this.statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createEvolutionTable(String tableName) {
        try {
            this.statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
            this.statement.executeUpdate("CREATE TABLE " + tableName + " (evolutions NUMERIC)");
        } catch (SQLException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveValueInTable(String tableName, int valueToSave) {
        try {
            this.statement.executeUpdate("INSERT INTO " + tableName + " (evolutions) VALUES (" + valueToSave + ")");
        } catch (SQLException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveDistributionInTable(String tableName, Distribution[] distributions) {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        stringBuilder.append(tableName);
        stringBuilder.append(" (");
        for (int i = 0; i < numberOfAgent; i++) {
            stringBuilder.append("agent");
            stringBuilder.append(i);
            if (i < numberOfAgent - 1) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append(")");
            }
        }
        stringBuilder.append(" VALUES (");
        for (int i = 0; i < numberOfAgent; i++) {
            stringBuilder.append("\"");
            stringBuilder.append(distributions[i].toString());
            stringBuilder.append("\"");
            if (i < numberOfAgent - 1) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append(")");
            }
        }
        System.out.println(stringBuilder.toString());
        try {
            this.statement.executeUpdate(stringBuilder.toString());
        } catch (SQLException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double[] getEvolutionsOf(String tableName, int iterations) throws SQLException {
        ResultSet ruleSet = statement.executeQuery("SELECT * FROM " + tableName + ";");
        double[] evolutions = new double[iterations];
        for (int i = 0; i < iterations; i++) {
            ruleSet.next();
            evolutions[i] = ruleSet.getInt("evolutions");
        }
        return evolutions;
    }

    private void createDistributionTable(String tableName) {
        try {
            this.statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
            StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
            stringBuilder.append(tableName);
            stringBuilder.append(" (");
            for (int i = 0; i < numberOfAgent; i++) {
                stringBuilder.append("agent");
                stringBuilder.append(i);
                stringBuilder.append(" TEXT");
                if (i < numberOfAgent - 1) {
                    stringBuilder.append(",");
                } else {
                    stringBuilder.append(")");
                }
            }
            this.statement.executeUpdate(stringBuilder.toString());

        } catch (SQLException ex) {
            Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
