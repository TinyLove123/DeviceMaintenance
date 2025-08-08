/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhom4.services.CategoryService;
import com.nhom4.services.DeviceService;
import com.nhom4.services.IncidentService;
import com.nhom4.services.MaintenanceScheduleService;
import com.nhom4.services.RepairTypeService;

/**
 *
 * @author Administrator
 */
@Controller
@ControllerAdvice
public class IndexController {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private CategoryService cateService;

    @Autowired
    private DeviceService DeviceService;

    @Autowired
    private RepairTypeService repairTypeService;
    
    @Autowired
    private IncidentService incidentService;
    
    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;
    



    @RequestMapping("/admin")
    @Transactional
    public String Home(Model model) {
        model.addAttribute("maintenanceSchedules", this.maintenanceScheduleService.getTodayMaintenanceSchedule());
        model.addAttribute("unapprovedIncidents", this.incidentService.getPreviousIncidentNotApproved());
        model.addAttribute("incidentCountToday", this.incidentService.countIncidentsToday());

        return "index";
    }

 
}
