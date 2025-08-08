/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.pojo.User;
import com.nhom4.services.MailService;
import com.nhom4.services.UserService;
import com.nhom4.utils.JwtUtils;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping(
            path = "/users",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> create(@RequestParam Map<String, String> info, @RequestParam("avatar") MultipartFile avatar) {
        User u = this.userService.addUser(info, avatar);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u) {
        if (this.userService.authenticate(u.getUsername(), u.getPassword())) {
            try {
                User user = userService.getUserByUsername(u.getUsername());
                String token = JwtUtils.generateToken(user.getUsername(), user.getUserRole());
                return ResponseEntity.ok(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi khi tạo JWT");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @RequestMapping("/secure/profile")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<User> getProfile(Principal principal) {
        return new ResponseEntity<>(this.userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/send-mail")
    @ResponseBody
    public String testSendMail() {
        mailService.sendMail("2251052036hieu@ou.edu.vn", "Hello!", "Nội dung test nè!");
        return "Đã gửi email!";
    }
}
