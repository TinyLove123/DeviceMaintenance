/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.RepairCost;
import com.nhom4.repositories.DeviceRepository;
import com.nhom4.repositories.MaintenanceScheduleRepository;
import com.nhom4.repositories.UserRepository;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public class DeviceRepositoryImpl implements DeviceRepository {

    private static final int PAGE_SIZE = 6;

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private MaintenanceScheduleRepository maintenanceScheduleRepo;
    @Autowired
    private UserRepository UserRepo;

    @Override
    public List<Device> getDevice(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Device> q = b.createQuery(Device.class);
        Root root = q.from(Device.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("nameDevice"), String.format("%%%s%%", kw)));
            }

            String cateId = params.get("categoryId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(root.get("categoryId").as(Integer.class), cateId));
            }

            q.where(predicates.toArray(Predicate[]::new));

            String orderBy = params.get("orderBy");
            if (orderBy != null && !orderBy.isEmpty()) {
                q.orderBy(b.asc(root.get(orderBy)));
            }
        }

        Query query = s.createQuery(q);

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);
        }

        return query.getResultList();

    }

    @Override
    public Device getDeviceById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Device> cq = cb.createQuery(Device.class);
        Root<Device> root = cq.from(Device.class);

        root.fetch("repairCostSet", JoinType.LEFT);
        root.fetch("locationSet", JoinType.LEFT);

        cq.select(root).where(cb.equal(root.get("id"), id)).distinct(true);

        return s.createQuery(cq).uniqueResult();
    }

    @Override
    public Device addOrUpdateDevice(Device d) {
        Session s = this.factory.getObject().getCurrentSession();
        if (d.getId() == null) {

            MaintenanceSchedule ms = new MaintenanceSchedule();
            ms.setDeviceId(d);
            d.getFrequency();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, d.getFrequency());
            Date calulatedDate = cal.getTime();
            ms.setStartDate(calulatedDate);
            ms.setDeviceId(d);
            ms.setProgress("in_completed");
            s.persist(d);
            this.maintenanceScheduleRepo.addOrUpdateMaintenanceSchedule(ms);
        } else {
            s.merge(d);
        }
        return d;
    }

    @Override
    public void deleteDevice(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Device d = this.getDeviceById(id);
        s.remove(d);
    }

    @Override
    public List<Device> getDevicesByCatesId(int id, Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Device> q = b.createQuery(Device.class);
        Root<Device> root = q.from(Device.class);
        q.select(root);

        List<Predicate> predicates = new ArrayList<>();
        // Thiết bị thuộc category có id cụ thể
        predicates.add(b.equal(root.get("categoryId").get("id"), id));

        if (params != null) {
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {

                // Điều kiện tìm kiếm theo category.type gần giống
                predicates.add(b.like(
                        b.lower(root.get("categoryId").get("type")),
                        "%" + kw.toLowerCase() + "%"
                ));
            }
        }

        // Gộp tất cả điều kiện
        q.where(b.and(predicates.toArray(new Predicate[0])));

        return s.createQuery(q).getResultList();
    }

    @Override
    public List<RepairCost> getRepairTypeByDeviceId(int id) {
        Session session = this.factory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<RepairCost> cq = cb.createQuery(RepairCost.class);
        Root<RepairCost> root = cq.from(RepairCost.class);

        cq.select(root)
                .where(cb.equal(root.get("deviceId").get("id"), id));

        return session.createQuery(cq).getResultList();
    }

    @Override
    public RepairCost addOrUpdateRepairCost(RepairCost repairCost) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (repairCost.getId() == null) {
                s.persist(repairCost);
            } else {
                s.merge(repairCost);
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi lưu RepairCost: " + e.getMessage());
        }
        return repairCost;
    }

    @Override
    public void deleteRepairCost(int repairId) {
        Session s = this.factory.getObject().getCurrentSession();

        RepairCost d = this.getRepairCostById(repairId);

        s.remove(d);

    }

    @Override
    public RepairCost getRepairCostById(int repairCostId) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(RepairCost.class, repairCostId);

    }

}
