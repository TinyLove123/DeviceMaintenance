/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.User;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Administrator
 */
public interface UserRepository {
    User getUserByUsername(String username);
    User addUser(User u);
    boolean authenticate(String username, String password);
    List<User> getUsers(Map<String,String> params);
    List<User> getEmployees();
    User getUserById(int id);
}