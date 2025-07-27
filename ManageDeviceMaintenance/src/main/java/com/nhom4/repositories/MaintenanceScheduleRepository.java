/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.MaintenanceSchedule;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface MaintenanceScheduleRepository {
    List<MaintenanceSchedule> getMaintenanceSchedules(Map<String,String> params);
    
    
    MaintenanceSchedule addMaintenanceSchedule();
    MaintenanceSchedule autoUpdateMaintenanceSchedule();
}
