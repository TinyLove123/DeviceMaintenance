/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.Category;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface CategoryRepository {
    List<Category> getCates();
    Category addOrUpdateCategory(Category c);
    Category getCatesById(int cateId);
    void deleteCates(int id);
}
