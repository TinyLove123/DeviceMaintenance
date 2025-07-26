/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.pojo.RepairType;
import com.nhom4.repositories.RepairTypeRepository;
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
public class RepairTypeRepositoryImpl implements RepairTypeRepository{

    
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<RepairType> getRepairType() {
       Session s = this.factory.getObject().getCurrentSession();
       Query   q = s.createQuery("FROM RepairType", RepairType.class);
       return q.getResultList();
    }

    @Override
    public RepairType addRepairType(RepairType RId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RepairType getRepairTypeById(int RId) {
        Session s = this.factory.getObject().getCurrentSession();

        return s.get(RepairType.class, RId);
    }
    
}
