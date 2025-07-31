/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services;

import com.nhom4.pojo.Incident;
import com.nhom4.pojo.RepairSchedule;
import com.nhom4.pojo.User;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface RepairScheduleService {

    void addOrUpdateRepairSchedule(Incident incident, RepairSchedule repair, User employee);

    void addRepair(int repairChedule);

    List<RepairSchedule> getRepairScheduleByDevice(int deviceId);

}
