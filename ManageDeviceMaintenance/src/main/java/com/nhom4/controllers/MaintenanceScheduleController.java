/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.services.MaintenanceScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Administrator
 */
@Controller

public class MaintenanceScheduleController {
    
    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;
    
    @GetMapping("/maintenanceSchedule")
    public String maintenanceScheduleView(){
        return "maintenanceSchedule";
    }
    
    @GetMapping("/addMaintenanceSchedule")
    public String addMaintenanceScheduleView(Model model) {
        model.addAttribute("maintenanceSchedules", new MaintenanceSchedule());
        return "addMaintenanceSchedule";
    }
    
//    @PostMapping("/add-maintenance-schedule")
//    public String add(@ModelAttribute("device") MaintenanceSchedule m) {
//        this.maintenanceScheduleService.addMaintenanceSchedule(m);
//        return "redirect:/devices-manager";
//    }
//    
//    @PostMapping("/add")
//    public String add(@ModelAttribute(value = "product") Product p) {
////        this.prodService.addOrUpdateProduct(p);
//        
//        return "redirect:/";
//    }
}
