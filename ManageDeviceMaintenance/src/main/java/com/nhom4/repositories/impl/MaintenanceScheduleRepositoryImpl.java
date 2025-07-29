/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.repositories.MaintenanceScheduleRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class MaintenanceScheduleRepositoryImpl implements MaintenanceScheduleRepository{

    private static final int PAGE_SIZE = 6;
    @Autowired
    private LocalSessionFactoryBean factory;
    
    public List<MaintenanceSchedule> getMaintenanceSchedules(Map<String,String> params) {
       Session s = this.factory.getObject().getCurrentSession();
       CriteriaBuilder b = s.getCriteriaBuilder();
       CriteriaQuery<MaintenanceSchedule> q = b.createQuery(MaintenanceSchedule.class);
        Root root = q.from(MaintenanceSchedule.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String startDate = params.get("startDate");
            if (startDate != null && !startDate.isEmpty()) {
                predicates.add(b.equal(root.get("startDate").as(Integer.class),startDate));
            }

//            String cateId = params.get("categoryId");
//            if (cateId != null && !cateId.isEmpty()) {
//                predicates.add(b.equal(root.get("categoryId").as(Integer.class), cateId));
//            }

            q.where(predicates.toArray(Predicate[]::new));

            String orderBy = params.get("orderBy");
            if (orderBy != null && !orderBy.isEmpty()) {
                q.orderBy(b.asc(root.get(orderBy)));
            }
        }

        Query query = s.createQuery(q);

//        if (params != null && params.containsKey("page")) {
//            int page = Integer.parseInt(params.get("page"));
//            int start = (page - 1) * PAGE_SIZE;
//
//            query.setMaxResults(PAGE_SIZE);
//            query.setFirstResult(start);
//        }

        return query.getResultList();
    }

    @Override
    public MaintenanceSchedule addMaintenanceSchedule(MaintenanceSchedule m) {
        Session s = this.factory.getObject().getCurrentSession();
        if (m.getId() == null) {
            s.persist(m);
        } else {
            s.merge(m);
        }
        return m;
    }

    @Override
    public MaintenanceSchedule autoUpdateMaintenanceSchedule() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MaintenanceSchedule autoAddMaintenanceSchedule(MaintenanceSchedule m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MaintenanceSchedule getDeviceById(int id) {
         Session s = this.factory.getObject().getCurrentSession();

        return s.get(MaintenanceSchedule.class, id);
    }
    
}
