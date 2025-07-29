/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.pojo.Category;
import com.nhom4.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Administrator
 */
@Controller

public class CategoryController {
    
    @Autowired
    private CategoryService cateService;
    
   @GetMapping("/categories")
   @Transactional
    public String maintenanceScheduleView(Model model){
        model.addAttribute("categories", cateService.getCates());
        model.addAttribute("category", new Category());
        return "categories";
    }
    
    @PostMapping("/add-categories")
    public String addCates(@ModelAttribute("category") Category c) {
        this.cateService.addOrUpdateCategory(c);
        return "categories";
    }
}
