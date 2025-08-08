/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.pojo.Incident;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.repositories.StatsRepository;
import com.nhom4.services.DeviceService;
import com.nhom4.services.MaintenanceScheduleService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;

    @Override
    public List<Object[]> statsRevenueByTime(String time, int year) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Object[]> statsRevenueByIncident() {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<Incident> root = cq.from(Incident.class);
        Join<Object, Object> deviceJoin = root.join("deviceId");

        var countExpr = cb.count(root);

        cq.multiselect(
            deviceJoin.get("id"),
            deviceJoin.get("nameDevice"),
            countExpr
        );

        cq.groupBy(deviceJoin.get("id"), deviceJoin.get("nameDevice"));
        cq.orderBy(cb.desc(countExpr));

        return session.createQuery(cq).getResultList();
    }

    @Override
    public List<Object[]> statsRevenueByMaintenanceSchedule() {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<MaintenanceSchedule> root = cq.from(MaintenanceSchedule.class);
        Join<Object, Object> deviceJoin = root.join("deviceId");

        var countExpr = cb.count(root);

        cq.multiselect(
            deviceJoin.get("id"),
            deviceJoin.get("nameDevice"),
            countExpr
        );

        cq.groupBy(deviceJoin.get("id"), deviceJoin.get("nameDevice"));
        cq.orderBy(cb.desc(countExpr));

        return session.createQuery(cq).getResultList();
    }

    @Override
    public List<Object[]> statsRevenueByMaintenanceScheduleHaveIncident() {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<MaintenanceSchedule> root = cq.from(MaintenanceSchedule.class);
        Join<Object, Object> deviceJoin = root.join("deviceId");

        Predicate hasIncident = cb.isNotNull(root.get("maintenanceIncidentLink"));

        var countExpr = cb.count(root);

        cq.multiselect(
            deviceJoin.get("id"),
            deviceJoin.get("nameDevice"),
            countExpr
        );

        cq.where(hasIncident);
        cq.groupBy(deviceJoin.get("id"), deviceJoin.get("nameDevice"));
        cq.orderBy(cb.desc(countExpr));

        return session.createQuery(cq).getResultList();
    }

}
