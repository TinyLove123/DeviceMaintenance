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
       return  s.get(Incident.class, id);
    }

    @Override
    public Incident addOrUpdateIncident(Incident incident, int deviceId, User user) {
        Session s = factory.getObject().getCurrentSession();


        Device device = s.get(Device.class, deviceId);
        incident.setDeviceId(device);
        incident.setSenderId(user);

        if (incident.getId() == null) {
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
                .where(cb.equal(root.get("device").get("id"), deviceId))
                .orderBy(cb.desc(root.get("reportDate"))); // sắp theo mới nhất

        Query query = s.createQuery(q);
        return query.getResultList();

    }

    @Override
    public Incident getNewIncident(int DeviceId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

}
