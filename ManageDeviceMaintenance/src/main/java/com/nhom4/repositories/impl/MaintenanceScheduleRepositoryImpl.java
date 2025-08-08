/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nhom4.dto.MaintenanceScheduleDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.MaintenanceIncidentLink;
import com.nhom4.pojo.MaintenanceReport;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.User;
import com.nhom4.repositories.DeviceRepository;
import com.nhom4.repositories.MaintenanceScheduleRepository;
import com.nhom4.services.IncidentService;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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

    @Autowired
    private IncidentService incidentService;

    @Override
    public List<MaintenanceSchedule> getMaintenanceSchedules(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<MaintenanceSchedule> q = b.createQuery(MaintenanceSchedule.class);
        Root<MaintenanceSchedule> root = q.from(MaintenanceSchedule.class);
        q.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (params != null) {
            String startDate = params.get("startDate");
            if (startDate != null && !startDate.isEmpty()) {
                predicates.add(b.equal(root.get("startDate").as(String.class), startDate));
            }

            String progress = params.get("progress");
            if (progress == null || progress.isEmpty()) {
                progress = "in_completed"; 
            }

            predicates.add(b.equal(root.get("progress"), progress));
        } else {
            predicates.add(b.equal(root.get("progress"), "in_completed"));
        }

        if (!predicates.isEmpty()) {
            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = s.createQuery(q);
        List<MaintenanceSchedule> result = query.getResultList();

        result.sort(Comparator.comparingInt(m -> {
            switch (m.getProgress()) {
                case "in_completed":
                    return 0;
                case "in_progress":
                    return 1;
                case "completed":
                    return 2;
                default:
                    return 3;
            }
        }));

        return result;
    }

    @Override
    public MaintenanceSchedule addOrUpdateMaintenanceSchedule(MaintenanceSchedule m) {
        Session s = this.factory.getObject().getCurrentSession();

        if (m.getId() == null) {

            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<MaintenanceSchedule> root = cq.from(MaintenanceSchedule.class);

            cq.select(cb.count(root));
            cq.where(
                    cb.and(
                            cb.equal(root.get("deviceId").get("id"), m.getDeviceId().getId()),
                            cb.equal(root.get("startDate"), m.getStartDate())
                    )
            );

            Long count = s.createQuery(cq).uniqueResult();

            if (count != null && count > 0) {
                throw new RuntimeException("Lịch bảo trì cho thiết bị này vào ngày này đã tồn tại!");
            }
            if (m.getIsAutoAdd() == null) {
                m.setIsAutoAdd(true); 
            }

            if (m.getProgress() == null || m.getProgress().isEmpty()) {
                m.setProgress("in_completed");
            }

            s.persist(m);
        } else {

            if (m.getStartDate() != null) {
                java.util.Date utilDate = new java.util.Date(m.getStartDate().getTime());
                LocalDate startDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (startDate.isAfter(LocalDate.now())) {
                    System.out.println("Chưa đến ngày bắt đầu, không cho cập nhật.");
                    MaintenanceSchedule dbSchedule = s.get(MaintenanceSchedule.class, m.getId());
                    m.setProgress(dbSchedule.getProgress());
                }
            }

            if (m.getDeviceId() != null) {
                m.setReceptStatus(m.getDeviceId().getStatusDevice());
            }

            if ("completed".equalsIgnoreCase(m.getProgress())) {
                s.merge(m); 

                if (Boolean.TRUE.equals(m.getIsAutoAdd())) {
                    autoAddMaintenanceSchedule(m);
                } else {
                    System.out.println("️ Không tạo lịch tiếp theo vì autoAddSchedule = false.");
                }
            } else {
                s.merge(m);
            }
        }

        return m;
    }

    @Override
    public void autoAddMaintenanceSchedule(MaintenanceSchedule m) {
        Session s = this.factory.getObject().getCurrentSession();
        Device device = m.getDeviceId();
        Integer frequency = device.getFrequency();

        if (frequency != null && frequency > 0) {
            LocalDate nextStartDate = LocalDate.now().plusDays(frequency);
            MaintenanceSchedule next = new MaintenanceSchedule();

            next.setDeviceId(device);
            next.setProgress("in_completed");
            next.setReceptStatus(device.getStatusDevice());
            next.setStartDate(java.sql.Date.valueOf(nextStartDate));
            next.setIsAutoAdd(m.getIsAutoAdd());

            s.persist(next);
            System.out.println(" Đã tạo lịch bảo trì mới sau " + frequency + " ngày.");
        } else {
            System.out.println("️ Frequency không hợp lệ.");
        }

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

        Predicate testPredicate = cb.lessThan(root.get("startDate"), new Date());
        cq.select(root).where(testPredicate);

        Query query = s.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public boolean hasMaintenanceReport(int maintenanceScheduleId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<MaintenanceReport> root = cq.from(MaintenanceReport.class);

        cq.select(cb.count(root));

        cq.where(cb.equal(root.get("maintenanceScheduleId").get("id"), maintenanceScheduleId));

        Long count = s.createQuery(cq).getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public void deleteMaintenanceSchedule(MaintenanceSchedule m) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            MaintenanceSchedule schedule = s.get(MaintenanceSchedule.class, m.getId());
            if (schedule != null && "in_completed".equalsIgnoreCase(schedule.getProgress())) {
                s.remove(schedule);
            } else {
                System.out.println("Không thể xóa lịch không ở trạng thái 'in_completed'");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void addMaintenanceIncidentLinkByEmployee(User user, int maintenanceId, Incident incident, String note, Date linkedAt) {
        Session s = this.factory.getObject().getCurrentSession();

        MaintenanceSchedule maintenance = this.getMaintenanceScheduleById(maintenanceId);

        Incident incidentSave = this.incidentService.addOrUpdateIncident(incident, maintenance.getDeviceId().getId(), user);

        MaintenanceIncidentLink link = new MaintenanceIncidentLink();
        link.setIncidentId(incidentSave);
        link.setMaintenanceScheduleId(maintenance);
        link.setNote(note);
        link.setLinkedAt(linkedAt);

        s.persist(link);
    }

}
