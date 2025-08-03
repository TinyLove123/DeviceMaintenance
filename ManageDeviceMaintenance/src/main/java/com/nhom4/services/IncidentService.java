/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface IncidentService {

    List<Incident> getIncident(Map<String, String> params);

    Incident getIncidentById(int id);

    Incident addOrUpdateIncident(Incident I, int DeviceId, User user);

    void deleteIncident(int id);

    List<Incident> GetIncidentByDeviceId(int deviceId);

    Incident getNewIncident(int DeviceId);
    
    List<Device> getListDeviceHadIncidentReport();
    
    List<Incident> getPreviousIncidentNotApproved();
    
    long countIncidentsToday();
}
