/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.pojo.Category;
import com.nhom4.repositories.CategoryRepository;
import com.nhom4.services.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository cateRepo;
     
    @Override
    public List<Category> getCates() {
        return this.cateRepo.getCates();
    }

    @Override
    public Category getCateById(int cateId) {
        return this.cateRepo.getCatesById(cateId);
    }

    @Override
    public Category addOrUpdateCategory(Category c) {
        return this.cateRepo.addOrUpdateCategory(c);
    }

    @Override
    public void deleteCates(int id) {

    }
    
}
