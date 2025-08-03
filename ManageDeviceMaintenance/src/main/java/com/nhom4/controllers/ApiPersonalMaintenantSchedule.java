/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhom4.dto.MaintenanceScheduleDTO;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.User;
import com.nhom4.services.MaintenanceScheduleService;
import com.nhom4.services.UserService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api/secure/persional-maintenance-schedule")
@CrossOrigin
public class ApiPersonalMaintenantSchedule {

    @Autowired
    private MaintenanceScheduleService maintenanceScheduleRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<MaintenanceScheduleDTO>> listMaintenanceSchedule(@RequestParam Map<String, String> params, Principal principal) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        return new ResponseEntity<>(this.maintenanceScheduleRepo.getMaintenanceSchedulesByUser(user), HttpStatus.OK);
    }

    @GetMapping("/persional-history-maintenance-schedule")
    public ResponseEntity<List<MaintenanceSchedule>> listHistoryMaintenanceSchedule(@RequestParam Map<String, String> params) {

        return new ResponseEntity<>(this.maintenanceScheduleRepo.getMaintenanceSchedules(params), HttpStatus.OK);
    }

    @GetMapping("/{id}/detail-maintenance-schedule")
    public ResponseEntity<MaintenanceSchedule> MaintenanceScheduleDetail(@PathVariable(value = "id") int id, Principal principal) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);
        if (!this.maintenanceScheduleRepo.isMaintenanceStaff(user, id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        return new ResponseEntity<>(this.maintenanceScheduleRepo.getMaintenanceScheduleById(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/detail-maintenance-schedule")
    public ResponseEntity<?> MaintenanceScheduleUpdate(
            @PathVariable(value = "id") int id,
            Principal principal, @RequestBody MaintenanceSchedule ms) {

        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        if (!this.maintenanceScheduleRepo.isMaintenanceStaff(user, id)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Bạn không có quyền truy cập thiết bị này"));
        }
        
        MaintenanceSchedule ms1 = this.maintenanceScheduleRepo.getMaintenanceScheduleById(id);
        ms1.setProgress(ms.getProgress());
        
        
        return ResponseEntity.ok(this.maintenanceScheduleRepo.addOrUpdateMaintenanceSchedule(ms1));
    }

}
