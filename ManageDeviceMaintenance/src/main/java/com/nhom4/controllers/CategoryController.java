/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 *
 * @author Administrator
 */
@Controller
@ControllerAdvice
public class CategoryController {
    
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private CategoryService cateService;

    
}
