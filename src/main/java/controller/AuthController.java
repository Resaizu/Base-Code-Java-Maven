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
    
    public static boolean Login(String Username, String Password) throws SQLException {
        AuthController Auth = new AuthController();
        
        List<Map<String, Object>> UserData = User.DB.Where("username", Username)
                .Where("password", Password).Get();
        
        if (!UserData.isEmpty()) {
            Auth.IsLoggedIn = true;
            return true;
        }
        
        Auth.IsLoggedIn = false;
        return false;
    }
    
    public static boolean Register(Map<String, Object> Request) throws SQLException {
        if(
            !User.DB.Where("username", Request.get("username").toString())
                .Get().isEmpty()
        ) {
            return false;
        }
        return User.DB.Create(Request) || false;
    }
}
