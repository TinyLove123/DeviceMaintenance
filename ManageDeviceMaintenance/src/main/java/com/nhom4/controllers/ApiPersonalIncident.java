/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.dto.IncidentDTO;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.User;
import com.nhom4.services.IncidentService;
import com.nhom4.services.UserService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiPersonalIncident {

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private UserService userService;
    //xử lý sự cố, thêm repair, tạo các repairdetail sủa chữa qua các repaircost

    @GetMapping("/secure/my-report-handle")
    public ResponseEntity<List<IncidentDTO>> getMyIncidentHandle(Principal principal) {

        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        List<IncidentDTO> myIncident = this.incidentService.getMyIncidentHandle(user);
        return new ResponseEntity<>(myIncident, HttpStatus.OK);
    }

    @GetMapping("/secure/my-report-handle/{id}/incident-detail")
    public ResponseEntity<?> getMyIncidentHandleDetail(@PathVariable("id") int incidentId,
            Principal principal) {

        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);
        if (!this.incidentService.checkHandleIncidentByUser(user, incidentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Bạn không có quyền truy cập thiết bị này"));
        }
        Incident incident = this.incidentService.getIncidentById(incidentId);
        return new ResponseEntity<>(incident, HttpStatus.OK);
    }

    @PostMapping("/secure/my-report-handle/{id}/incident-update-status")
    public ResponseEntity<?> updateMyIncidentHandleDetail(@PathVariable("id") int incidentId,
            @RequestBody Incident incident, Principal principal) {
        String username = principal.getName();

        User user = this.userService.getUserByUsername(username);
        if (!this.incidentService.checkHandleIncidentByUser(user, incidentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Bạn không có quyền truy cập thiết bị này"));
        }
        Incident incidentRoot = this.incidentService.getIncidentById(incidentId);
        incidentRoot.setStatus(incident.getStatus());
        this.incidentService.addOrUpdateIncident(incidentRoot, incidentRoot.getDeviceId().getId(), user);

        return new ResponseEntity<>(incidentRoot, HttpStatus.OK);
    }
}
