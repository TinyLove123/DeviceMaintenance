/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.pojo.Incident;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.services.DeviceService;
import com.nhom4.services.StatsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/stats/{deviceId}")
    public String deviceStats(@PathVariable("deviceId") int deviceId, Model model) {
        // Lấy thông tin thiết bị nếu cần hiển thị tên
        model.addAttribute("device", deviceService.getDeviceById(deviceId));

        // Lấy danh sách sự kiện (bảo trì + sự cố)
        model.addAttribute("events", statsService.statsRevenueByDevice(deviceId));

        return "statsDetail";
    }
    
    @GetMapping("/stats")
    public String listDevicesForStats(Model model,@RequestParam Map<String, String> params) {
        model.addAttribute("devices", deviceService.getDevice(params)); // giả sử getDevices() trả về List<Device>
        return "stats";
    }
}
