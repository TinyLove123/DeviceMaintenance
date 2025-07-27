/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services;

import com.nhom4.pojo.Category;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface CategoryService {
    List<Category> getCates();
    Category getCateById(int cateId);
}
