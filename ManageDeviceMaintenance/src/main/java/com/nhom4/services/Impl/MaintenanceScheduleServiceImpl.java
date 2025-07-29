/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.repositories.MaintenanceScheduleRepository;
import com.nhom4.services.MaintenanceScheduleService;
import java.util.List;
import java.util.Map;
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
    public MaintenanceSchedule addMaintenanceSchedule(MaintenanceSchedule m) {
       return this.maintenanceScheduleRepo.addMaintenanceSchedule(m);
    }

    @Override
    public MaintenanceSchedule autoUpdateMaintenanceSchedule() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MaintenanceSchedule getDeviceById(int id) {
        return this.maintenanceScheduleRepo.getDeviceById(id);
    }
    
}
