/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.MaintenanceReport;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author Administrator
 */
public interface MaintenanceReportRepository {
    List<MaintenanceReport> getMaintenanceReports(Map<String, String> params);
    
    MaintenanceReport addOrUpdateMaintenanceReport(MaintenanceReport r);
    
    MaintenanceReport getMaintenanceReportByScheduleId(int id);
    
    
}
