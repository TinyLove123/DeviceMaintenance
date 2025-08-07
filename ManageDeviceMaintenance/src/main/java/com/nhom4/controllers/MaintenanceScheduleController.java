/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.RepairCost;
import com.nhom4.pojo.RepairType;
import com.nhom4.pojo.User;
import com.nhom4.repositories.MaintenanceScheduleRepository;
import com.nhom4.services.DeviceService;
import com.nhom4.services.MaintenanceScheduleService;
import com.nhom4.services.UserService;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class MaintenanceScheduleController {

    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;

    @Autowired
    private MaintenanceScheduleRepository maintenanceScheduleRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/maintenance-schedule-manager")
    public String maintenanceScheduleView(Model model, @RequestParam Map<String, String> params) {

        if (!params.containsKey("progress") || params.get("progress").isEmpty()) {
            params.put("progress", "in_completed");
        }

        List<User> employees = userService.getEmployee();
        model.addAttribute("maintenanceSchedules", maintenanceScheduleService.getMaintenanceSchedules(params));
        model.addAttribute("employee", employees);
        model.addAttribute("params", params);
        model.addAttribute("maintenanceSchedule", new MaintenanceSchedule());

        return "maintenanceSchedule";
    }

    @GetMapping("/maintenance-schedule-manager/{id}/maintenance-schedule-detail")
    public String getDetailMaintenanceSchedule(Model model, @PathVariable("id") Integer maintenanceId) {

        model.addAttribute("employees", this.userService.getEmployee());
        model.addAttribute("maintenanceSchedule", this.maintenanceScheduleService.getMaintenanceScheduleById(maintenanceId));

        return "maintenanceScheduleDetail";
    }

    @PostMapping("/changeEmployee")
    public String changeEmployee(@RequestParam("scheduleId") int scheduleId,
            @RequestParam("employeeId") int employeeId) {
        MaintenanceSchedule schedule = maintenanceScheduleService.getMaintenanceScheduleById(scheduleId);
        if (schedule != null) {
            User employee = userService.getUserById(employeeId);
            if (employee != null) {
                schedule.setEmployeeId(employee); // Cập nhật employee
                maintenanceScheduleService.addOrUpdateMaintenanceSchedule(schedule); // Gọi service đã viết
            }
        }
        return "redirect:/admin/maintenance-schedule-manager";
    }

    @PostMapping("/auto-add-schedule")
    public String AutoAddSchedule(@RequestParam("scheduleId") int scheduleId,
            @RequestParam("currentState") boolean currentState) {
        MaintenanceSchedule ms = maintenanceScheduleService.getMaintenanceScheduleById(scheduleId);
        if (ms != null) {
            ms.setIsAutoAdd(!currentState);
            maintenanceScheduleService.addOrUpdateMaintenanceSchedule(ms);
        }
        return "redirect:/admin/maintenance-schedule-manager";
    }

    @GetMapping("/maintenance-schedule-manager/add-maintenance-schedule")
    public String addMaintenanceScheduleForm(Model model, @RequestParam Map<String, String> params) {
        try {
            model.addAttribute("maintenanceSchedule", new MaintenanceSchedule());
            model.addAttribute("employees", userService.getEmployee());
            model.addAttribute("devices", deviceService.getDevice(params));//
            return "addMaintenanceSchedule";
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());

            return "admin/add-maintenance-schedule";
        }

    }

    @PostMapping("/maintenance-schedule-manager/add-maintenance-schedule")
    public String addMaintenanceSchedule(@ModelAttribute("maintenanceSchedule") MaintenanceSchedule m, Model model, @RequestParam Map<String, String> params) {
        try {
            maintenanceScheduleService.addOrUpdateMaintenanceSchedule(m);
            return "redirect:/admin/maintenance-schedule-manager";
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("maintenanceSchedule", m);
            model.addAttribute("employees", userService.getEmployee());
            model.addAttribute("devices", deviceService.getDevice(params));
            return "addMaintenanceSchedule"; // Hiển thị lại form thêm
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping("/maintenance-schedule-manager/delete")
    public String deleteMaintenanceSchedule(@RequestParam("id") int id) {
        MaintenanceSchedule m = new MaintenanceSchedule();
        m.setId(id);
        maintenanceScheduleService.deleteMaintenanceSchedule(m);
        System.out.println("Gọi vào controller xóa");

        return "redirect:/admin/maintenance-schedule-manager";
    }

//    @GetMapping("/send-mail")
//    @ResponseBody
//    public String testSendMail() {
//        mailService.sendMail("2251052036hieu@ou.edu.vn", "Hello!", "Nội dung test nè!");
//        return "Đã gửi email!";
//    }
}
