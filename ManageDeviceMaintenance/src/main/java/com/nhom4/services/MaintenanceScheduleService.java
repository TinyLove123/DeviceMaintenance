/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom4.services;

import com.nhom4.pojo.MaintenanceSchedule;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface MaintenanceScheduleService {
    List<MaintenanceSchedule> getMaintenanceSchedules(Map<String,String> params);
    
    MaintenanceSchedule getDeviceById(int id);
    MaintenanceSchedule addMaintenanceSchedule(MaintenanceSchedule m);
    MaintenanceSchedule autoUpdateMaintenanceSchedule();
}
