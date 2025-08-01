/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.pojo.Incident;
import com.nhom4.pojo.RepairSchedule;
import com.nhom4.pojo.User;
import com.nhom4.repositories.RepairScheduleRepository;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class RepairScheduleRepositoryImpl implements RepairScheduleRepository{

    @Override
    public void addOrUpdateRepairSchedule(Incident incident, RepairSchedule repairSchedule, User employee) {
        if(repairSchedule.getId()!=0){
            repairSchedule.setEmployeeId(employee);
            repairSchedule.setIncidentId(incident);
        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
