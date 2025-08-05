/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.dto.DeviceOutputDTO;
import com.nhom4.dto.IncidentDTO;
import com.nhom4.dto.MaintenanceScheduleDTO;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.User;
import com.nhom4.repositories.IncidentRepository;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public class IncidentRepositoryImpl implements IncidentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Incident> getIncident(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Incident> cq = cb.createQuery(Incident.class);
        Root<Incident> root = cq.from(Incident.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params != null && params.containsKey("status")) {
            String status = params.get("status");
            predicates.add(cb.equal(root.get("status"), status));
        }

        if (params != null && params.containsKey("deviceId")) {
            int deviceId = Integer.parseInt(params.get("deviceId"));
            predicates.add(cb.equal(root.get("deviceId").get("id"), deviceId));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        Expression<Object> statusPriority = cb.selectCase(root.get("status").as(String.class))
                .when("PENDING_APPROVAL", 1)
                .when("APPROVED", 2)
                .when("IN_PROGRESS", 3)
                .when("RESOLVED", 4)
                .otherwise(5);

        Expression<Object> emergencyPriority = cb.selectCase()
                .when(cb.and(
                        cb.equal(root.get("status"), "PENDING_APPROVAL"),
                        cb.isTrue(root.get("isEmergency"))
                ), 1)
                .otherwise(0);

        Expression<Object> dateSort = cb.selectCase(root.get("status").as(String.class))
                .when("PENDING_APPROVAL", root.get("reportDate"))
                .when("APPROVED", root.get("approvalDate"))
                .when("IN_PROGRESS", root.get("startDate"))
                .when("RESOLVED", root.get("endDate"))
                .otherwise(root.get("reportDate"));

        cq.orderBy(
                cb.asc(statusPriority),
                cb.desc(emergencyPriority),
                cb.desc(dateSort)
        );

        Query query = s.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Incident getIncidentById(int id) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Incident> cq = cb.createQuery(Incident.class);
        Root<Incident> root = cq.from(Incident.class);

        root.fetch("deviceId", JoinType.LEFT)
                .fetch("repairCostSet", JoinType.LEFT); 

        cq.select(root).where(cb.equal(root.get("id"), id));

        return s.createQuery(cq).uniqueResult();
    }

    @Override
    public Incident addOrUpdateIncident(Incident incident, int deviceId, User user) {
        Session s = factory.getObject().getCurrentSession();

        Device device = s.get(Device.class, deviceId);
        incident.setDeviceId(device);
        incident.setSenderId(user);

        if (incident.getId() == null) {
            incident.setStatus("PENDING_APPROVAL");
            incident.setReportDate(new Date());
            s.persist(incident);

        } else {
            s.merge(incident);
        }

        return incident;
    }

    @Override
    public void deleteIncident(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Incident> GetIncidentByDeviceId(int deviceId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Incident> q = cb.createQuery(Incident.class);
        Root root = q.from(Incident.class);
        q.select(root)
                .where(cb.equal(root.get("deviceId").get("id"), deviceId))
                .orderBy(cb.desc(root.get("reportDate"))); // sắp theo mới nhất

        Query query = s.createQuery(q);
        return query.getResultList();

    }

    @Override
    public Incident getNewIncident(int deviceId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Incident> cq = cb.createQuery(Incident.class);
        Root<Incident> root = cq.from(Incident.class);

        Join<Device, Incident> deviceJoin = root.join("deviceId");

        Predicate p1 = cb.equal(deviceJoin.get("id"), deviceId);
        Predicate p2 = cb.equal(root.get("status"), "PENDING_APPROVAL");

        cq.select(root)
                .where(cb.and(p1, p2))
                .orderBy(cb.desc(root.get("id")));

        TypedQuery<Incident> query = s.createQuery(cq);
        query.setMaxResults(1);

        List<Incident> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);

    }

    @Override
    public List<Device> getListDeviceHadIncidentReport() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Device.class);
        Root<Device> deviceRoot = cq.from(Device.class);

        Join<Device, Incident> incidentJoin = deviceRoot.join("incidentSet");

        Predicate newIncident = cb.equal(incidentJoin.get("status"), "PENDING_APPROVAL");

        cq.select(deviceRoot).where(newIncident);

        return s.createQuery(cq).getResultList();

    }

    @Override
    public List<Incident> getPreviousIncidentNotApproved() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Incident> cq = cb.createQuery(Incident.class);
        Root<Incident> root = cq.from(Incident.class);

        // Lấy thời điểm bắt đầu của ngày hôm nay
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        Date startOfTodayDate = Date.from(startOfToday.atZone(ZoneId.systemDefault()).toInstant());

        // Tạo các điều kiện:
        // 1. Incident có reportDate trước ngày hôm nay
        // 2. Có trạng thái PENDING_APPROVAL
        Predicate datePredicate = cb.lessThan(root.get("reportDate"), startOfTodayDate);
        Predicate statusPredicate = cb.equal(root.get("status"), "PENDING_APPROVAL");

        // Kết hợp các điều kiện
        cq.select(root)
                .where(cb.and(datePredicate, statusPredicate));

        // Sắp xếp:
        // 1. Ưu tiên incident khẩn cấp (isEmergency = 1)
        // 2. Theo ngày báo cáo (cũ nhất trước)
        // 3. Có thể thêm sắp xếp phụ theo ID nếu cần
        cq.orderBy(
                cb.desc(root.get("isEmergency")), // Emergency first
                cb.asc(root.get("reportDate")), // Oldest first
                cb.asc(root.get("id")) // Additional ordering if needed
        );

        return session.createQuery(cq).getResultList();
    }

    @Override
    public long LcountIncidentsToday() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Incident> root = cq.from(Incident.class);
        LocalDate today = LocalDate.now();
        Date startOfDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        cq.select(cb.count(root)).where(cb.between(root.get("reportDate"),
                startOfDay,
                endOfDay));

        return s.createQuery(cq).getSingleResult();
    }

    

    @Override
    public Boolean checkHandleIncidentByUser(User user, int incidentId) {
             Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root root = cq.from(Incident.class);
        cq.select(cb.count(root))
                .where(
                        cb.and(
                                cb.equal(root.get("id"), incidentId),
                                cb.equal(root.get("employeeId").get("id"), user.getId())
                        ));
        Long count = s.createQuery(cq).uniqueResult();
        return count != null && count > 0;
        
    }

    @Override
    public List<IncidentDTO> getMyIncidentHandle(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();

        CriteriaQuery<Incident> cq = cb.createQuery(Incident.class);
        Root<Incident> root = cq.from(Incident.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("employeeId"), user));
//        predicates.add(root.get("status").in("APPROVED", "IN_PROGRESS", "RESOLVED"));

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("reportDate")));

        TypedQuery<Incident> q = s.createQuery(cq);
        List<Incident> results = q.getResultList();

        // Mapping manually to DTO
        return results.stream().map(incident -> new IncidentDTO(
                incident.getId(),
                incident.getTitle(),
                incident.getDetailDescribe(),
                incident.getStatus(),
                incident.getReportDate(),
                incident.getApprovalDate(),
                incident.getIsEmergency(),
                incident.getStartDate(),
                incident.getEndDate(),
                incident.getApprovedBy(),
                incident.getEmployeeId(),
                incident.getSenderId()
        )).collect(Collectors.toList());
            
    }

    
    

}
