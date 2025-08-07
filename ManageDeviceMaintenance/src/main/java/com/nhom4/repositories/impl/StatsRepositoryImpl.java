/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.dto.StatsDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.RepairCost;
import com.nhom4.repositories.StatsRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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

    @Override
    public List<StatsDTO> statsRevenueByDevice(Integer deviceId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        List<StatsDTO> results = new ArrayList<>();

        // --- 1. Thống kê lịch bảo trì ---
        CriteriaQuery<Object[]> msQuery = cb.createQuery(Object[].class);
        Root<MaintenanceSchedule> msRoot = msQuery.from(MaintenanceSchedule.class);
        msQuery.select(cb.array(
                msRoot.get("id"),
                msRoot.get("startDate"), // ✅ Thuộc tính đúng
                msRoot.get("progress")
        ));
        msQuery.where(cb.equal(msRoot.get("deviceId").get("id"), deviceId));

        List<Object[]> maintenanceList = s.createQuery(msQuery).getResultList();
        for (Object[] row : maintenanceList) {
            results.add(new StatsDTO(
                    "maintenance",
                    (Integer) row[0],
                    (Date) row[1],
                    (String) row[2]
            ));
        }

        // --- 2. Thống kê sự cố ---
        CriteriaQuery<Object[]> incidentQuery = cb.createQuery(Object[].class);
        Root<Incident> incidentRoot = incidentQuery.from(Incident.class);
        incidentQuery.select(cb.array(
                incidentRoot.get("id"),
                incidentRoot.get("reportDate"), // hoặc startDate nếu bạn muốn
                incidentRoot.get("title")
        ));
        incidentQuery.where(cb.equal(incidentRoot.get("deviceId").get("id"), deviceId));

        List<Object[]> incidentList = s.createQuery(incidentQuery).getResultList();
        for (Object[] row : incidentList) {
            results.add(new StatsDTO(
                    "incident",
                    (Integer) row[0],
                    (Date) row[1],
                    (String) row[2]
            ));
        }

        // --- (Tuỳ chọn) Sắp xếp theo ngày ---
        results.sort(Comparator.comparing(StatsDTO::getDate));

        return results;
    }

    @Override
    public List<Object[]> statsRevenueByTime(String time, int year) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
