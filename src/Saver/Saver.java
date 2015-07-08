package Saver;

import Saver.exception.*;
import java.sql.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Saver {

    private String[] fieldNames;
    private String[] fieldTypes;
    private int fieldCount;
    private String name;
    private Connection connection;

    public Saver(String name, String[] tableFieldsNames, String[] tableFieldsTypes) throws SaverCreationException {

        this.name = name;
        this.fieldTypes = tableFieldsTypes;
        this.fieldNames = tableFieldsNames;
        this.fieldCount = this.fieldNames.length;
        if (this.fieldCount != this.fieldTypes.length) {
            throw new SaverCreationException();
        }
        this.connection = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            createConnection();
            stmt = connection.createStatement();
            String sql = createSqlStringToCreateTable();
            stmt.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SaverCreationException();
        }
    }

    public void save(String[] row) throws SavingException {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        sqlBuilder.append(this.name);
        sqlBuilder.append("VALUES (");
        if(this.fieldCount == row.length){
        for (int i = 0; i < this.fieldCount; i++) {
            String fieldContent = row[i];
            String type = fieldTypes[i];
            if(type.equalsIgnoreCase("TEXT")){
                sqlBuilder.append("\"");
            }
            sqlBuilder.append(name);
            if(type.equalsIgnoreCase("TEXT")){
                sqlBuilder.append("\"");
            }
            
            sqlBuilder.append(" ");
        }
        sqlBuilder.append(");");
        } else{
            throw new SavingException();
        }

    }
    
    private void createConnection() throws SQLException{
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.name + ".db");
    }

    private String createSqlStringToCreateTable() {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE ");
        sqlBuilder.append(this.name);
        sqlBuilder.append(" (");
        for (int i = 0; i < fieldCount; i++) {
            String name = fieldNames[i];
            String type = fieldTypes[i];
            sqlBuilder.append(name);
            sqlBuilder.append(" ");
            sqlBuilder.append(type);
            if(i!=fieldCount-1){
                sqlBuilder.append(", ");
            }
        }
        sqlBuilder.append(")");
        System.out.println(sqlBuilder);
        return sqlBuilder.toString();
    }
}
