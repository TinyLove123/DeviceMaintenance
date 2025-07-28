/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.services.DeviceService;
import com.nhom4.services.MaintenanceScheduleService;
import com.nhom4.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Administrator
 */
@Controller

public class MaintenanceScheduleController {
    
    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DeviceService deviceService;
    
    
    @GetMapping("/maintenanceSchedule")
    public String maintenanceScheduleView(Model model,@RequestParam Map<String, String> params){
        model.addAttribute("maintenanceSchedules", maintenanceScheduleService.getMaintenanceSchedules(params));
        return "maintenanceSchedule";
    }
    
    
    
    @PostMapping("/admin/add-maintenance-schedule")
    public String add(@ModelAttribute("maintenanceSchedules") MaintenanceSchedule m) {
        this.maintenanceScheduleService.addMaintenanceSchedule(m);
        return "redirect:/maintenanceSchedule";
    }
    
    @GetMapping("/{id}")
    public String changeEmployee(@PathVariable Integer id, Model model) {
        MaintenanceSchedule ms= this.maintenanceScheduleService.getDeviceById(id);
        if (ms == null) {
            return "redirect:/admin/devices-manager";
        }
        model.addAttribute("maintenanceSchedule", ms);
        return "maintenanceSchedule";
    }
//    
//    @PostMapping("/add")
//    public String add(@ModelAttribute(value = "product") Product p) {
////        this.prodService.addOrUpdateProduct(p);
//        
//        return "redirect:/";
//    }
}
