/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.dto.MaintenanceScheduleDTO;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.User;
import com.nhom4.repositories.MaintenanceScheduleRepository;
import com.nhom4.services.MaintenanceScheduleService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class MaintenanceScheduleServiceImpl implements MaintenanceScheduleService{

    @Autowired
    private MaintenanceScheduleRepository maintenanceScheduleRepo;
    
    @Override
    public List<MaintenanceSchedule> getMaintenanceSchedules(Map<String, String> params) {
        return this.maintenanceScheduleRepo.getMaintenanceSchedules(params);
    }

    @Override
    public MaintenanceSchedule addOrUpdateMaintenanceSchedule(MaintenanceSchedule m) {
       return this.maintenanceScheduleRepo.addOrUpdateMaintenanceSchedule(m);
    }

    

    @Override
    public MaintenanceSchedule getMaintenanceScheduleById(int id) {
        return this.maintenanceScheduleRepo.getMaintenanceScheduleById(id);
    }

    @Override
    public List<MaintenanceScheduleDTO> getMaintenanceSchedulesByUser(User user) {
        return this.maintenanceScheduleRepo.getMaintenanceSchedulesByUser(user);
    }

    @Override
    public Boolean isMaintenanceStaff(User u, int msId) {
        return this.maintenanceScheduleRepo.isMaintenanceStaff(u, msId);
    }

    @Override
    public List<MaintenanceSchedule> getTodayMaintenanceSchedule() {
        return this.maintenanceScheduleRepo.getTodayMaintenanceSchedule();
    }

    @Override
    public boolean hasMaintenanceReport(int maintenanceScheduleId) {
        return this.maintenanceScheduleRepo.hasMaintenanceReport(maintenanceScheduleId);
    }

    @Override
    public void autoAddMaintenanceSchedule(MaintenanceSchedule m) {
         this.maintenanceScheduleRepo.autoAddMaintenanceSchedule(m);
    }

    @Override
    public void deleteMaintenanceSchedule(MaintenanceSchedule m) {
        this.maintenanceScheduleRepo.deleteMaintenanceSchedule(m);
        
    }
    
    @Override
    public void addMaintenanceIncidentLinkByEmployee(User user, int maintenanceId, Incident incident, String note, Date linkedBy) {
        this.maintenanceScheduleRepo.addMaintenanceIncidentLinkByEmployee(user, maintenanceId, incident, note, linkedBy);
    }

    
}
