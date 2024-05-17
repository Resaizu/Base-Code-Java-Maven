/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author TATS
 */
import Model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

public class UserController {
    public static List<Map<String, Object>> Index(Map<String, Object> Request) throws SQLException {
        Object Search = Request.get("search");
        return User.DB.Where("Name", "%" + Search + "%", "LIKE").Get();
    }

//    public boolean Store(User user) {
//        //
//    }
}