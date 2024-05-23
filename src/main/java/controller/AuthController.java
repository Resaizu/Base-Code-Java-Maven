/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.User;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Resaizu
 */
public class AuthController {
    public boolean IsLoggedIn = false;
    
    /**
     * Login a User
     * 
     * @param username
     * @param password
     * @return
     * @throws SQLException 
     */
    public static boolean login(String username, String password) throws SQLException {
        AuthController Auth = new AuthController();
        
        List<Map<String, Object>> UserData = User.DB.where("username", username)
                .where("password", password).get();
        
        if (!UserData.isEmpty()) {
            Auth.IsLoggedIn = true;
            return true;
        }
        
        Auth.IsLoggedIn = false;
        return false;
    }
    
    /**
     * Register a User
     * 
     * @param request
     * @return
     * @throws SQLException 
     */
    public static boolean register(Map<String, Object> request) throws SQLException {
        if(
            !User.DB.where("username", request.get("username").toString())
                .get().isEmpty()
        ) {
            return false;
        }
        return User.DB.create(request);
    }
}
