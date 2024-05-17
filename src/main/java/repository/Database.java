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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author TATSING_DENNIS
 */
public class Database {
    private String TableName;
    private String WhereQuery = "";
    
    public Database(String TableName) {
       this.TableName = TableName; 
    }
    
    private Connection GetConnection() {
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
    
    public boolean Create(Map<String, Object> UserData) {
        Connection conn = this.GetConnection();
        
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        List<Object> params = new ArrayList<>();
        for (Map.Entry<String, Object> entry : UserData.entrySet()) {
            if (columns.length() > 0) {
                columns.append(", ");
                values.append(", ");
            }
            columns.append(entry.getKey());
            values.append("?");
            params.add(entry.getValue());
        }
        try {
            String sql = "INSERT INTO "+ this.TableName +" (" + columns.toString() + ") VALUES (" + values.toString() + ")";
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Database Where(String ColumnName, String Value) {
        if (!WhereQuery.isEmpty()) {
            WhereQuery += " AND ";
        }
        else {
            WhereQuery += "WHERE ";
        }
        
        WhereQuery += ColumnName + " = '" + Value + "'";
        return this;
    }
    
    public Database Where(String ColumnName, String Value, String Comparison) {
        if (!WhereQuery.isEmpty()) {
            WhereQuery += " AND ";
        }
        else {
            WhereQuery += "WHERE ";
        }
        
        WhereQuery += ColumnName + " " + Comparison + " '" + Value + "'";
        return this;
    }

//    public Database orderBy(String orderBy) {
//        orderByQuery = orderBy;
//        return this;
//    }
//
//    public Database limit(int limit) {
//        this.limit = limit;
//        return this;
//    }
    
    public List<Map<String, Object>> Get() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();

        try {
            conn = this.GetConnection();
            stmt = conn.createStatement();

            String sql = "SELECT * FROM " + this.TableName + " " + WhereQuery.toString();
            this.WhereQuery = "";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                result.add(row);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
}
