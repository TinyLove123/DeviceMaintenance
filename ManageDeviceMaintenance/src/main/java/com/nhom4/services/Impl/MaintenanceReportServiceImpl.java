/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.pojo.MaintenanceReport;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhom4.repositories.MaintenanceReportRepository;
import com.nhom4.services.MaintenanceReportService;

/**
 *
 * @author Administrator
 */
@Service
public class MaintenanceReportServiceImpl implements MaintenanceReportService {

    @Autowired
    private MaintenanceReportRepository maintenanceScheduleReportRepo;

    @Override
    public List<MaintenanceReport> getMaintenanceReports(Map<String, String> params) {
        return this.maintenanceScheduleReportRepo.getMaintenanceReports(params);
    }

    @Override
    public MaintenanceReport addOrUpdateMaintenanceReport(MaintenanceReport r) {
        return this.maintenanceScheduleReportRepo.addOrUpdateMaintenanceReport(r);
    }

    @Override
    public MaintenanceReport getMaintenanceReportByScheduleId(int id) {
        return this.maintenanceScheduleReportRepo.getMaintenanceReportByScheduleId(id);
    }

}
