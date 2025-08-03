/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.dto.MaintenanceScheduleDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.User;
import com.nhom4.repositories.DeviceRepository;
import com.nhom4.repositories.MaintenanceScheduleRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
public class MaintenanceScheduleRepositoryImpl implements MaintenanceScheduleRepository {

    private static final int PAGE_SIZE = 6;
    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private DeviceRepository deviceRepo;

    public List<MaintenanceSchedule> getMaintenanceSchedules(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<MaintenanceSchedule> q = b.createQuery(MaintenanceSchedule.class);
        Root root = q.from(MaintenanceSchedule.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String startDate = params.get("startDate");
            if (startDate != null && !startDate.isEmpty()) {
                predicates.add(b.equal(root.get("startDate").as(Integer.class), startDate));
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
    public MaintenanceSchedule addOrUpdateMaintenanceSchedule(MaintenanceSchedule m) {
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
    public MaintenanceSchedule getMaintenanceScheduleById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        MaintenanceSchedule ms = s.get(MaintenanceSchedule.class, id);
        Device d = this.deviceRepo.getDeviceById(ms.getDeviceId().getId());
        ms.setDeviceId(d);
        return ms;
    }

    @Override
    public MaintenanceSchedule setEmployee(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        return null;
    }

    @Override
    public List<MaintenanceScheduleDTO> getMaintenanceSchedulesByUser(User user) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<MaintenanceSchedule> cq = cb.createQuery(MaintenanceSchedule.class);
        Root<MaintenanceSchedule> root = cq.from(MaintenanceSchedule.class);

        cq.select(root).where(cb.equal(root.get("employeeId"), user));

        List<MaintenanceSchedule> results = session.createQuery(cq).getResultList();

        List<MaintenanceScheduleDTO> dtos = results.stream().map(ms -> {
            return new MaintenanceScheduleDTO(
                    ms.getId(),
                    ms.getStartDate(),
                    ms.getProgress(),
                    ms.getDeviceId() != null ? ms.getDeviceId().getId() : null,
                    ms.getDeviceId() != null ? ms.getDeviceId().getNameDevice() : null,
                    ms.getEmployeeId() != null ? ms.getEmployeeId().getId() : null,
                    ms.getEmployeeId() != null ? ms.getEmployeeId().getUsername() : null
            );
        }).toList();

        return dtos;
    }

    @Override
    public Boolean isMaintenanceStaff(User u, int msId) {
        Session s = this.factory.getObject().getCurrentSession();
        MaintenanceSchedule loaded = s.get(MaintenanceSchedule.class, msId);

        return u != null && loaded != null
                && loaded.getEmployeeId() != null
                && u.getId().equals(loaded.getEmployeeId().getId());
    }

    @Override
    public List<MaintenanceSchedule> getTodayMaintenanceSchedule() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<MaintenanceSchedule> cq = cb.createQuery(MaintenanceSchedule.class);
        Root<MaintenanceSchedule> root = cq.from(MaintenanceSchedule.class);

        LocalDate today = LocalDate.now();
        Date startOfDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Predicate testPredicate = cb.lessThan(root.get("startDate"), new Date()); // lấy tất cả ngày trước hiện tại
        cq.select(root).where(testPredicate);

        Query query = s.createQuery(cq);
        return query.getResultList();
    }

}
