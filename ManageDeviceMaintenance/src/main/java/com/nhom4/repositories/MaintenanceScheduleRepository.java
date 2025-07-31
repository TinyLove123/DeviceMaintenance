/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface MaintenanceScheduleRepository {
    List<MaintenanceSchedule> getMaintenanceSchedules(Map<String,String> params);
    
    MaintenanceSchedule getMaintenanceScheduleById(int id);
    MaintenanceSchedule addOrUpdateMaintenanceSchedule(MaintenanceSchedule m);
    MaintenanceSchedule autoUpdateMaintenanceSchedule();
    MaintenanceSchedule autoAddMaintenanceSchedule(MaintenanceSchedule m);
    MaintenanceSchedule setEmployee(User u);

}
