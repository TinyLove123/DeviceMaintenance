/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.MaintenanceReport;
import com.nhom4.repositories.DeviceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nhom4.repositories.MaintenanceReportRepository;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public class MaintenanceReportRepositoryImpl implements MaintenanceReportRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private DeviceRepository deviceRepo;

    @Override
    public List<MaintenanceReport> getMaintenanceReports(Map<String, String> params) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MaintenanceReport addOrUpdateMaintenanceReport(MaintenanceReport r) {
        Session s = this.factory.getObject().getCurrentSession();

        
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<MaintenanceReport> cq = cb.createQuery(MaintenanceReport.class);
        Root<MaintenanceReport> root = cq.from(MaintenanceReport.class);

        cq.select(root).where(cb.equal(
                root.get("maintenanceScheduleId").get("id"),
                r.getMaintenanceScheduleId().getId()
        ));

        List<MaintenanceReport> existingReports = s.createQuery(cq).getResultList();

        if (r.getId() == null && existingReports.isEmpty()) {
            if (r.getReportDate() == null) {
                r.setReportDate(new Date());
            }

            s.persist(r);
        } else {
   
            if (r.getId() == null && !existingReports.isEmpty()) {
                r.setId(existingReports.get(0).getId());
            }

            s.merge(r);
        }

        return r;
    }

    @Override
    public MaintenanceReport getMaintenanceReportByScheduleId(int id) {
        Session session = this.factory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<MaintenanceReport> cq = cb.createQuery(MaintenanceReport.class);
        Root<MaintenanceReport> root = cq.from(MaintenanceReport.class);

        cq.select(root).where(cb.equal(root.get("maintenanceScheduleId").get("id"), id));

        List<MaintenanceReport> results = session.createQuery(cq).getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

}
