/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.pojo.Incident;
import com.nhom4.pojo.User;
import com.nhom4.services.IncidentService;
import com.nhom4.services.UserService;
import jakarta.ws.rs.PathParam;
import java.security.Principal;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class IncidentController {

    @Autowired
    private IncidentService incidentRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/incident-manager")
    public String getDeviceListHadIncident(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("Incidents", this.incidentRepo.getIncident(params));

        return "incidentManager";
    }

    @GetMapping("/incident-manager/{id}/incident-detail")
    public String getDetailIncident(Model model, @PathVariable("id") Integer incidentId) {

        model.addAttribute("employees", this.userService.getEmployee());
        model.addAttribute("Incident", this.incidentRepo.getIncidentById(incidentId));

        return "incidentDetail";
    }

    @PostMapping("/incident-device/{deviceId}/incident-update/{incidentId}")
    public String updateIncidentStatus(
            @PathVariable("incidentId") Integer incidentId,
            @RequestParam("status") String status,
            @RequestParam(value = "employeeId", required = false) Integer employeeId,
            Principal principal
    ) {
        String username = principal.getName();
        User adminUser = this.userService.getUserByUsername(username);

        Incident incident = this.incidentRepo.getIncidentById(incidentId);

        if (incident != null) {
            incident.setStatus(status);
            incident.setApprovedBy(adminUser);
            incident.setApprovalDate(new Date());

            if ("APPROVED".equalsIgnoreCase(status) && employeeId != null) {
                User employee = this.userService.getUserById(employeeId);
                incident.setEmployeeId(employee);
            }

            this.incidentRepo.addOrUpdateIncident(incident, incident.getDeviceId().getId(), adminUser);
        }

        return "redirect:/admin/incident-manager";
    }

}
