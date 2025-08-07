/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom4.services;

import com.nhom4.dto.MaintenanceScheduleDTO;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.User;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author Administrator
 */
public interface MaintenanceScheduleService {

    List<MaintenanceSchedule> getMaintenanceSchedules(Map<String, String> params);

    List<MaintenanceScheduleDTO> getMaintenanceSchedulesByUser(User user);

    MaintenanceSchedule getMaintenanceScheduleById(int id);

    MaintenanceSchedule addOrUpdateMaintenanceSchedule(MaintenanceSchedule m);

    void autoAddMaintenanceSchedule(MaintenanceSchedule m);

    Boolean isMaintenanceStaff(User u, int msId);
    
    List<MaintenanceSchedule> getTodayMaintenanceSchedule();
    
    boolean hasMaintenanceReport(int maintenanceScheduleId);
    
    void addMaintenanceIncidentLinkByEmployee(User user, int maintenanceId ,Incident incident, String note, Date linkedBy);


}
