/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Resaizu
 */
public class Database {
    private String tableName;
    private String whereQuery = "";
    private String whereData = "";
    
    public Database(String tableName) {
       this.tableName = tableName; 
    }
    
    /**
     * Connection From Database
     * 
     * @return 
     */
    private Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/course_project";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch(SQLException|ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
    /**
     * Create Another Row of Data
     * 
     * @param data
     * @return 
     */
    public boolean create(Map<String, Object> data) {
        Connection conn = getConnection();
        
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        List<Object> params = new ArrayList<>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (columns.length() > 0) {
                columns.append(", ");
                values.append(", ");
            }
            columns.append(entry.getKey());
            values.append("?");
            params.add(entry.getValue());
        }
        try {
            String Query = "INSERT INTO " + this.tableName + " (" + columns.toString() + ") VALUES (" + values.toString() + ")";
            PreparedStatement pStmt = conn.prepareStatement(Query);
            for (int i = 0; i < params.size(); i++) {
                pStmt.setObject(i + 1, params.get(i));
            }
            int result = pStmt.executeUpdate();
            pStmt.close();
            return result > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Update Specified Column's/s Data
     * 
     * @param data
     * @param id
     * @return 
     */
    public boolean update(Map<String, Object> data, int id) {
        Connection conn = getConnection();
        
        StringBuilder setClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (setClause.length() > 0) {
                setClause.append(", ");
            }
            setClause.append(entry.getKey()).append(" = ?");
            params.add(entry.getValue());
        }
        params.add(id);
        
        try {
            String query = "UPDATE " + this.tableName + " SET " + setClause.toString() + " WHERE id = ?";
            PreparedStatement pStmt = conn.prepareStatement(query);
            for (int i = 0; i < params.size(); i++) {
                pStmt.setObject(i + 1, params.get(i));
            }
            int result = pStmt.executeUpdate();
            pStmt.close();
            return result > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Delete the Entire Row of Data
     * 
     * @param id
     * @return 
     */
    public boolean delete(int id) {
        Connection conn = getConnection();
        String query = "Delete FROM " + this.tableName + " WHERE id = ?";
        
        try {
            PreparedStatement pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, id);
            int result = pStmt.executeUpdate();
            pStmt.close();
            return result > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Find a Data Using Column Name
     * 
     * @param columnName
     * @param value
     * @return 
     */
    public Database where(String columnName, String value) {
        if (!whereQuery.isEmpty()) {
            whereQuery += " AND ";
        }
        else {
            whereQuery += "WHERE ";
        }
        
        whereQuery += columnName + " = BINARY ?";
        whereData += value +",";
        return this;
    }
    
    /**
     * @Overload
     * Find a Data Using Column Name and A Comparison
     * 
     * @param columnName
     * @param value
     * @param comparison
     * @return 
     */
    public Database where(String columnName, String value, String comparison) {
        if (!whereQuery.isEmpty()) {
            whereQuery += " AND ";
        }
        else {
            whereQuery += "WHERE ";
        }
        
        whereQuery += columnName + " " + comparison + " ?";
        whereData += value + ",";
        return this;
    }
    
    /**
     * 
     * 
     * @param columnName
     * @param value
     * @return 
     */
    public Database orWhere(String columnName, String value) {
        if (whereData.isEmpty()) {
            throw new IllegalStateException("orWhere() called without where() clause.");
        } else {
            whereQuery += " OR ";
        }
        
        whereQuery += columnName + " = BINARY ?";
        whereData += value +",";
        return this;
    }
    
    public Database orWhere(String columnName, String value, String comparison) {
        if (whereData.isEmpty()) {
            throw new IllegalStateException("orWhere() called without where() clause.");
        } else {
            whereQuery += " OR ";
        }
        
        whereQuery += columnName + " " + comparison + " ?";
        whereData += value + ",";
        return this;
    }
    
    /**
     * Get All Data From a Table
     * 
     * @return
     * @throws SQLException 
     */
    public List<Map<String, Object>> get() throws SQLException {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> Result = new ArrayList<>();

        try {
            conn = getConnection();
            String query = "SELECT * FROM " + this.tableName + " " + whereQuery;
            
            pStmt = conn.prepareStatement(query);
            if(!whereData.isEmpty()) {
                String[] WhereStrings = whereData.split(",");
                for (int i = 0; i < WhereStrings.length; i++) {
                    if(!WhereStrings[i].isEmpty()) {
                        pStmt.setString(i + 1, WhereStrings[i]);
                    }
                }
            }
            whereQuery = "";
            whereData = "";
            rs = pStmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                Result.add(row);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pStmt != null) {
                pStmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return Result;
    }
}
