/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Repository.Database;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Resaizu
 */
public class User {
    public static Database DB = new Database("users");
    public static boolean isLoggedIn = false;
}