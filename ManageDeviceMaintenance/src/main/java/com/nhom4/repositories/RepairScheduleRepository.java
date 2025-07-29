/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.RepairSchedule;
import java.util.List;

/**
 *
 * @author Administrator
 */

public interface RepairScheduleRepository {
    void addOrUpdateRepairSchedule(int incidentId, RepairSchedule repair);
    void addRepair(int repairChedule);
    List<RepairSchedule> getRepairScheduleByDevice(int deviceId);
    
}
