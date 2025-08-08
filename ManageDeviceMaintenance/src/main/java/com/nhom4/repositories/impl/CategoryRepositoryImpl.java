/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.pojo.Category;
import com.nhom4.repositories.CategoryRepository;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Category> getCates() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Category", Category.class);
        return q.getResultList();

    }

    @Override
    public Category addOrUpdateCategory(Category c) {
        Session s = this.factory.getObject().getCurrentSession();
        if (c.getId() == null) {
            s.persist(c);
        } else {
            s.merge(c);
        }
        return c;

    }

    @Override
    public Category getCatesById(int cateId) {
        Session s = this.factory.getObject().getCurrentSession();

        return s.get(Category.class, cateId);
    }

    @Override
    public void deleteCates(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

}
