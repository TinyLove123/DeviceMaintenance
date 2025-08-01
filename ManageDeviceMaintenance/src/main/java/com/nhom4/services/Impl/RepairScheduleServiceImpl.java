/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.pojo.Incident;
import com.nhom4.pojo.RepairSchedule;
import com.nhom4.pojo.User;
import com.nhom4.repositories.RepairScheduleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 */
public class RepairScheduleServiceImpl implements RepairScheduleRepository{
    
    @Autowired
    private RepairScheduleRepository repairScheduleRepo;

    @Override
    public void addOrUpdateRepairSchedule(Incident incident, RepairSchedule repair, User employee) {
        this.repairScheduleRepo.addOrUpdateRepairSchedule(incident, repair, employee);
    }

    @Override
    public void addRepair(int repairChedule) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<RepairSchedule> getRepairScheduleByDevice(int deviceId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
