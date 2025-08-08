/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
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

import com.nhom4.pojo.Incident;
import com.nhom4.pojo.User;
import com.nhom4.services.IncidentService;
import com.nhom4.services.MailService;
import com.nhom4.services.RepairService;
import com.nhom4.services.UserService;

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
        
           
        

        this.incidentService.addOrUpdateIncident(incident, incidentForm.getDeviceId().getId(), adminUser);
        

        return "redirect:/admin/incident-manager";
    }

    @GetMapping("/incident-manager/{id}/detail-repair")
    public String getIncidentRepair(
            Model model, @PathVariable("id") Integer incidentId,
            Principal principal) {
        model.addAttribute("Repair", this.repairService.getRepairByIncident(incidentId));

        return "repairDetail";
    }

    @PostMapping("/send-incident-mail/{id}/incident")
    @ResponseBody
    public String sendIncidentMail(@PathVariable("id") Integer incidentId, Principal principal) {
        
        String username = principal.getName();
        User adminUser = this.userService.getUserByUsername(username);
        Incident incident = this.incidentService.getIncidentById(incidentId);
        if (incident == null) {
            return "Dữ liệu sự cố không hợp lệ";
        }

        String toEmail = adminUser.getEmail(); // giả sử Sender là người nhận
        String subject = "[Báo cáo sự cố] " + incident.getTitle();

        StringBuilder content = new StringBuilder();
        content.append("<h3>Thông tin sự cố</h3>");
        content.append("<p><strong>Tiêu đề:</strong> ").append(incident.getTitle()).append("</p>");
        content.append("<p><strong>Mô tả:</strong> ").append(incident.getDetailDescribe()).append("</p>");

        if (incident.getDeviceId() != null) {
            content.append("<p><strong>Thiết bị:</strong> ").append(incident.getDeviceId().getNameDevice()).append("</p>");
        }

        content.append("<p><strong>Trạng thái:</strong> ").append(incident.getStatus()).append("</p>");
        content.append("<p><strong>Người gửi:</strong> ")
                .append(incident.getSenderId().getFirstName()).append(" ")
                .append(incident.getSenderId().getLastName()).append("</p>");

        if (incident.getEmployeeId() != null) {
            content.append("<p><strong>Nhân viên xử lý:</strong> ")
                    .append(incident.getEmployeeId().getFirstName()).append(" ")
                    .append(incident.getEmployeeId().getLastName()).append("</p>");
        }

        mailService.sendHtmlMail(toEmail, subject, content.toString());

        return "Đã gửi email thành công!";
    }

}
