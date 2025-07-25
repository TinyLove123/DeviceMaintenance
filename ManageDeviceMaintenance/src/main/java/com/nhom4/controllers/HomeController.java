/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.pojo.AdministrativeUnits;
import com.nhom4.pojo.Category;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Administrator
 */
@Controller
public class HomeController {
    @Autowired
    private LocalSessionFactoryBean  factory;
    
    @RequestMapping("/")
    @Transactional
    public String Home(Model model){
        model.addAttribute("msg","Hi chào cậu");
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM AdministrativeUnits", AdministrativeUnits.class);
        model.addAttribute("units", q.getResultList());
        
        return "index";
    }
    
}
