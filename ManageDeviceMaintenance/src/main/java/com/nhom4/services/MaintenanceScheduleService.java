/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom4.services;

import com.nhom4.dto.MaintenanceScheduleDTO;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface MaintenanceScheduleService {

    List<MaintenanceSchedule> getMaintenanceSchedules(Map<String, String> params);

    List<MaintenanceScheduleDTO> getMaintenanceSchedulesByUser(User user);

    MaintenanceSchedule getMaintenanceScheduleById(int id);

    MaintenanceSchedule addOrUpdateMaintenanceSchedule(MaintenanceSchedule m);

    MaintenanceSchedule autoUpdateMaintenanceSchedule();

    Boolean isMaintenanceStaff(User u, int msId);
    
    List<MaintenanceSchedule> getTodayMaintenanceSchedule();
}
