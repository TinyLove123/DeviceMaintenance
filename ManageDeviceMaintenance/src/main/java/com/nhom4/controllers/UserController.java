/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.pojo.User;
import com.nhom4.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@Controller
public class UserController {

     @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/admin/user-manager")
    @Transactional
    public String listUser(Model model,
            @RequestParam Map<String, String> params) {
        List<User> users = this.userService.getUsers(params);
        model.addAttribute("users", users);
        return "userManager";
    }

    @GetMapping("/admin/user-manager/add-user")
    public String addUserView(Model model) {
        model.addAttribute("userAdd");

        return "addUser";
    }

    @PostMapping("/admin/user-manager/add-user")
    public String addUser(@RequestParam Map<String, String> params,
            @RequestParam("avatar") MultipartFile avatar,
            Model model) {
        try {
            userService.addUser(params, avatar);
            return "redirect:/admin/user-manager";
        } catch (RuntimeException ex) {
             model.addAttribute("error", ex.getMessage());
            return "addUser";
        }
    }
}
