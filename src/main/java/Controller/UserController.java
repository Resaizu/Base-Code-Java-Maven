/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Resaizu
 */
import Model.User;
import java.sql.SQLException;
import java.util.List;

import java.util.Map;

public class UserController {
    /**
     * Display a List of data
     * 
     * @param request
     * @return
     * @throws SQLException 
     */
    public static List<Map<String, Object>> index(Map<String, Object> request) throws SQLException {
        Object search = request.get("search");
        return User.DB.where("name", "%" + search + "%", "LIKE")
                .orWhere("username", "%" + search + "%", "LIKE").get();
    }
    
    /**
     * Store Newly Created Data From Storage
     * 
     * @param request
     * @return
     * @throws SQLException 
     */
    public static boolean store(Map<String, Object> request) throws SQLException {
        return User.DB.create(request);
    }
    
    /**
     * Update the Specified Data From Storage
     * 
     * @param request
     * @param id
     * @return
     * @throws SQLException 
     */
    public static boolean update(Map<String, Object> request, int id) throws SQLException {
        return User.DB.update(request, id);
    }
    
    /**
     * Remove the Specified Data From Storage
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    public static boolean delete(int id) throws SQLException {
        return User.DB.delete(id);
    }
}