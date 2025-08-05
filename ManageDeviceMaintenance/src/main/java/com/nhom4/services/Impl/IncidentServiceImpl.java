/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.dto.IncidentDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.User;
import com.nhom4.repositories.IncidentRepository;
import com.nhom4.services.IncidentService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Service
@Transactional
public class IncidentServiceImpl implements IncidentService{
    
    @Autowired
    private IncidentRepository incidentRepo;

    @Override
    public List<Incident> getIncident(Map<String, String> params) {
        return this.incidentRepo.getIncident(params);
    }

    @Override
    public Incident getIncidentById(int id) {
        return this.incidentRepo.getIncidentById(id);
    }

    @Override
    public Incident addOrUpdateIncident(Incident I, int DeviceId,User user) {
        return this.incidentRepo.addOrUpdateIncident(I,DeviceId,user);
    }

    @Override
    public void deleteIncident(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Incident> GetIncidentByDeviceId(int deviceId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Incident getNewIncident(int DeviceId) {
        return this.incidentRepo.getNewIncident(DeviceId);
    }

    @Override
    public List<Device> getListDeviceHadIncidentReport() {
        return this.incidentRepo.getListDeviceHadIncidentReport();
    }

    @Override
    public List<Incident> getPreviousIncidentNotApproved() {
        return this.incidentRepo.getPreviousIncidentNotApproved();
    }

    @Override
    public long countIncidentsToday() {
        return this.incidentRepo.LcountIncidentsToday();
    }

    @Override
    public Boolean checkHandleIncidentByUser(User user, int incidentId) {
        return this.incidentRepo.checkHandleIncidentByUser(user, incidentId);
    }

    @Override
    public List<IncidentDTO> getMyIncidentHandle(User user) {
        return this.incidentRepo.getMyIncidentHandle(user);

    }

    
    
    
   

   
    
}
