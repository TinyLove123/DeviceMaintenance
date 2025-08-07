/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom4.services;

import com.nhom4.pojo.MaintenanceReport;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface MaintenanceReportService {
     List<MaintenanceReport> getMaintenanceReports(Map<String, String> params);
    
    MaintenanceReport addOrUpdateMaintenanceReport(MaintenanceReport r);
    
    MaintenanceReport getMaintenanceReportByScheduleId(int id);
}
