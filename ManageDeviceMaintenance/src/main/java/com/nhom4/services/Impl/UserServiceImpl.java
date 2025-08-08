/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhom4.pojo.User;
import com.nhom4.repositories.UserRepository;
import com.nhom4.services.UserService;

/**
 *
 * @author Administrator
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        if (u.getIsDel() == Boolean.TRUE) {
            throw new DisabledException("Account is disabled");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();

        String role = u.getUserRole().toUpperCase();
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        authorities.add(new SimpleGrantedAuthority(role));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                true,
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                authorities);
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User u = new User();
        u.setFirstName(params.get("firstName"));
        u.setLastName(params.get("lastName"));
        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));
        u.setUsername(params.get("username"));
        u.setSex(params.get("sex"));
        u.setJoinDate(new Date());
        u.setIsDel(Boolean.FALSE);
        u.setPassword(this.passwordEncoder.encode(params.get("password")));

        String role = params.get("userRole");
        if (role != null) {
            u.setUserRole(role.toUpperCase()); 
        }
        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.userRepo.addUser(u);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    @Override
    public List<User> getUsers(Map<String, String> params) {
        return this.userRepo.getUsers(params);
    }

    @Override
    public List<User> getEmployee() {
        return this.userRepo.getEmployees();
    }

    @Override
    public User getUserById(int id) {
        return this.userRepo.getUserById(id);
    }

}
