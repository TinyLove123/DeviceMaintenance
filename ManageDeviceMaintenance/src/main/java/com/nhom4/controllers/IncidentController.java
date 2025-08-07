/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.pojo.Incident;
import com.nhom4.pojo.User;
import com.nhom4.services.IncidentService;
import com.nhom4.services.MailService;
import com.nhom4.services.RepairService;
import com.nhom4.services.UserService;
import jakarta.ws.rs.PathParam;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.Date;
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
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private RepairService repairService;
    
    @Autowired
    private MailService mailService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/incident-manager")
    public String getDeviceListHadIncident(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("Incidents", this.incidentService.getIncident(params));

        return "incidentManager";
    }

    @GetMapping("/incident-manager/{id}/incident-detail")
    public String getDetailIncident(Model model, @PathVariable("id") Integer incidentId) {
        model.addAttribute("now", new Date());

        model.addAttribute("employees", this.userService.getEmployee());
        model.addAttribute("Incident", this.incidentService.getIncidentById(incidentId));

        return "incidentDetail";
    }

    @PostMapping("/incident-device/{incidentId}/incident-update")
    public String updateIncidentStatus(
            @ModelAttribute("Incident") Incident incidentForm,
            Principal principal) {
        
        String username = principal.getName();
        User adminUser = this.userService.getUserByUsername(username);

        Incident incident = this.incidentService.getIncidentById(incidentForm.getId());
        if (incident == null) {
            return "redirect:/admin/incident-manager?error=notFound";
        }

        if (!"PENDING_APPROVAL".equalsIgnoreCase(incident.getStatus())) {
            return "redirect:/admin/incident-manager?error=alreadyProcessed";
        }

        incident.setStatus(incidentForm.getStatus());
        incident.setApprovedBy(adminUser);
        incident.setApprovalDate(new Date());

        if ("APPROVED".equalsIgnoreCase(incidentForm.getStatus()) && incidentForm.getEmployeeId() != null) {
            incident.setEmployeeId(incidentForm.getEmployeeId());
            incident.setStartDate(incidentForm.getStartDate());
            incident.setEndDate(incidentForm.getEndDate());
        }
        if (incident.getEmployeeId() != null && incident.getEmployeeId().getEmail() != null) {
            String email = incident.getEmployeeId().getEmail();
            String subject = "Bạn được giao xử lý sự cố";
            String body = "Bạn được phân công xử lý sự cố thiết bị: "
                    + incidentForm.getDeviceId().getNameDevice()
                    + ". Thời gian bắt đầu: "
                    + incident.getStartDate();
            try {
                mailService.sendMail(email, subject, body);
            } catch (Exception e) {
                System.out.println("loi gui mail: " + e.getMessage());
            }
        }
    

        this.incidentService.addOrUpdateIncident(incident, incidentForm.getDeviceId().getId(), adminUser);
        

        return "redirect:/admin/incident-manager";
    }
    
    @GetMapping("/incident-manager/{id}/detail-repair")
    public String getIncidentRepair(
           Model model,@PathVariable("id") Integer incidentId,
            Principal principal) {
        model.addAttribute("Repair", this.repairService.getRepairByIncident(incidentId));
        
        return "repairDetail";
    }
  
        
    

}
