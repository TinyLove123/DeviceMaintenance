/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.dto.MaintenanceScheduleDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.User;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author Administrator
 */
public interface MaintenanceScheduleRepository {

    List<MaintenanceSchedule> getMaintenanceSchedules(Map<String, String> params);

    List<MaintenanceScheduleDTO> getMaintenanceSchedulesByUser(User user);

    MaintenanceSchedule getMaintenanceScheduleById(int id);

    MaintenanceSchedule addOrUpdateMaintenanceSchedule(MaintenanceSchedule m);

    void autoAddMaintenanceSchedule(MaintenanceSchedule m);

    MaintenanceSchedule setEmployee(User u);

    Boolean isMaintenanceStaff(User u, int msId);

    List<MaintenanceSchedule> getTodayMaintenanceSchedule();

    boolean hasMaintenanceReport(int maintenanceScheduleId);

    
}
