/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.configs.CustomSecurityException;
import com.nhom4.pojo.Device;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nhom4.pojo.Incident;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.User;
import com.nhom4.repositories.IncidentRepository;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Date;

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Incident getIncidentById(int id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Incident.class, id);
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

}
