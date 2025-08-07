/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.dto.IncidentDTO;
import com.nhom4.dto.RepairDetailDTO;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.Repair;
import com.nhom4.pojo.RepairCost;
import com.nhom4.pojo.User;
import com.nhom4.services.DeviceService;
import com.nhom4.services.IncidentService;
import com.nhom4.services.RepairService;
import com.nhom4.services.UserService;
import jakarta.persistence.criteria.Predicate;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    private RepairService repairService;
    
    @Autowired
    private DeviceService deviceService;

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
                    .body(Map.of("error", "Bạn không có quyền truy cập báo cáo này"));
        }
        Incident incident = this.incidentService.getIncidentById(incidentId);
        return new ResponseEntity<>(incident, HttpStatus.OK);
    }

    @PutMapping("/secure/my-report-handle/{id}/incident-update-status")
    public ResponseEntity<?> updateMyIncidentHandleDetail(
            @PathVariable("id") int incidentId,
            @RequestParam("status") String status,
            Principal principal) {

        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        if (!this.incidentService.checkHandleIncidentByUser(user, incidentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Bạn không có quyền truy cập báo cáo này"));
        }

        Incident incidentRoot = this.incidentService.getIncidentById(incidentId);
        incidentRoot.setStatus(status);

        this.incidentService.addOrUpdateIncident(incidentRoot, incidentRoot.getDeviceId().getId(), user);
        return new ResponseEntity<>(incidentRoot, HttpStatus.OK);
    }

    @PostMapping("/secure/my-report-handle/{id}/add-repair")
    public ResponseEntity<?> addRepair(
            @PathVariable("id") int incidentId, @RequestBody Repair request, @RequestParam Map<String, String> params, Principal principal
    ) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);
        if (!this.incidentService.checkHandleIncidentByUser(user, incidentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Bạn không có quyền truy cập báo cáo này"));
        }
        Incident incident = this.incidentService.getIncidentById(incidentId);
        this.repairService.addOrUpdateRepairByIncidentId(request, incident, user, params);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/secure/report-handle/{id}/add-repair-detail")
    public ResponseEntity<?> addRepairDetail(@PathVariable("id") int incidentId,
            @RequestBody List<RepairDetailDTO> repairDetail) {
        try {
            Repair repair = this.repairService.getRepairByIncident(incidentId);

            if (repair == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Không tìm thấy bản sửa chữa ứng với sự cố ID: " + incidentId));
            }

            this.repairService.addRepairDetail(repair, repairDetail);
            return ResponseEntity.ok(Map.of("message", "Thêm chi tiết sửa chữa thành công"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace(); // in stack trace ra log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Đã xảy ra lỗi hệ thống. Vui lòng thử lại sau."));
        }
    }

    @GetMapping("/secure/my-report-handle/{id}/repair-detail")
    public ResponseEntity<?> addRepairDetail(@PathVariable("id") int incidentId) {
        Repair repair = this.repairService.getRepairByIncident(incidentId);
        return new ResponseEntity<>(repair, HttpStatus.OK);
    }
    
    @DeleteMapping("/secure/my-report-handle/{id}/delete-repair-detail")
    public ResponseEntity<?> deleteRepairDetail(@PathVariable("id") int incidentId) {
        this.repairService.deleteRepairDetail(incidentId);
        return new ResponseEntity<>( HttpStatus.OK);
    }
    
    
    
    @GetMapping("/secure/{id}/repair-cost")
    public ResponseEntity<?> getRepairCost(@PathVariable("id") int deviceId) {
        List<RepairCost> repairCost = this.deviceService.getRepairCostByDeviceId(deviceId);
        return new ResponseEntity<>(repairCost, HttpStatus.OK);
    }
}
