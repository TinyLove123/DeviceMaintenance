/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.dto.AddIncidentLinkRequestDTO;
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
import com.nhom4.dto.MaintenanceReportDTO;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.MaintenanceSchedule;
import com.nhom4.pojo.MaintenanceReport;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.User;
import com.nhom4.services.IncidentService;
import com.nhom4.services.MaintenanceScheduleService;
import com.nhom4.services.UserService;
import static jakarta.ws.rs.client.Entity.entity;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import com.nhom4.services.MaintenanceReportService;
import java.text.ParseException;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api/secure/persional-maintenance-schedule")
@CrossOrigin
public class ApiPersonalMaintenantSchedule {

    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private MaintenanceReportService maintenanceReportService;

    @Autowired
    private IncidentService incidentService;

    @GetMapping("/")
    public ResponseEntity<List<MaintenanceScheduleDTO>> listMaintenanceSchedule(@RequestParam Map<String, String> params, Principal principal) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        return new ResponseEntity<>(this.maintenanceScheduleService.getMaintenanceSchedulesByUser(user), HttpStatus.OK);
    }

    @GetMapping("/persional-history-maintenance-schedule")
    public ResponseEntity<List<MaintenanceSchedule>> listHistoryMaintenanceSchedule(@RequestParam Map<String, String> params) {

        return new ResponseEntity<>(this.maintenanceScheduleService.getMaintenanceSchedules(params), HttpStatus.OK);
    }

    @GetMapping("/{id}/detail-maintenance-schedule")
    public ResponseEntity<MaintenanceSchedule> MaintenanceScheduleDetail(@PathVariable(value = "id") int id, Principal principal) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);
        if (!this.maintenanceScheduleService.isMaintenanceStaff(user, id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        return new ResponseEntity<>(this.maintenanceScheduleService.getMaintenanceScheduleById(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/detail-maintenance-schedule")
    public ResponseEntity<?> MaintenanceScheduleUpdate(
            @PathVariable(value = "id") int id,
            Principal principal, @RequestBody MaintenanceSchedule ms) {

        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        if (!this.maintenanceScheduleService.isMaintenanceStaff(user, id)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Bạn không có quyền truy cập thiết bị này"));
        }

        MaintenanceSchedule ms1 = this.maintenanceScheduleService.getMaintenanceScheduleById(id);
        if ("completed".equalsIgnoreCase(ms1.getProgress())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Lịch bảo trì đã hoàn thành, không thể cập nhật"));
        }

        if ("completed".equalsIgnoreCase(ms.getProgress())) {
            boolean hasReport = this.maintenanceScheduleService.hasMaintenanceReport(id); // ⚠️ Bạn cần viết hàm này

            if (!hasReport) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Vui lòng tạo báo cáo bảo trì trước khi hoàn thành."));
            }
        }

        ms1.setProgress(ms.getProgress());

        return ResponseEntity.ok(this.maintenanceScheduleService.addOrUpdateMaintenanceSchedule(ms1));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/{id}/detail-maintenance-report")
    public ResponseEntity<MaintenanceReportDTO> MaintenanceReportDetail(
            @PathVariable(value = "id") int id,
            Principal principal) {

        // Lấy username hiện tại
        String username = principal.getName();

        // Lấy thông tin user từ username
        User user = this.userService.getUserByUsername(username);

        // Kiểm tra quyền: có phải nhân viên của lịch này không?
        if (!this.maintenanceScheduleService.isMaintenanceStaff(user, id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        // Lấy báo cáo từ lịch bảo trì
        MaintenanceReport report = this.maintenanceReportService
                .getMaintenanceReportByScheduleId(id);

        // Nếu không có báo cáo nào thì trả về 404
        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MaintenanceReportDTO dto = new MaintenanceReportDTO();
        dto.setId(report.getId());
        dto.setDescription(report.getDescription());
        dto.setPrice(report.getPrice());
        dto.setMaintenanceRate(report.getMaintenanceRate());
        dto.setReportDate(report.getReportDate());

        // Trả về báo cáo
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/{id}/detail-maintenance-report")
    public ResponseEntity<?> MaintenanceReportUpdate(
            @PathVariable(value = "id") int id,
            Principal principal, @RequestBody MaintenanceReport r) {

        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        if (!this.maintenanceScheduleService.isMaintenanceStaff(user, id)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Bạn không có quyền truy cập thiết bị này"));
        }

        MaintenanceSchedule schedule = this.maintenanceScheduleService.getMaintenanceScheduleById(id);
        if (schedule == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Không tìm thấy lịch bảo trì"));
        }

        // Gán các thông tin liên quan vào báo cáo
        r.setMaintenanceScheduleId(schedule);
        r.setEmployeeId(user);

        if (r.getReportDate() == null) {
            r.setReportDate(new Date());
        }

        // Thêm hoặc cập nhật
        try {
            MaintenanceReport saved = this.maintenanceReportService.addOrUpdateMaintenanceReport(r);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        }

    }

    @PostMapping("/maintenance-schedule/{id}/add-link")
    public ResponseEntity<?> addLink(
            @RequestBody AddIncidentLinkRequestDTO request,
            @PathVariable("id") int maintenanceScheduleId,
            Principal principal
    ) {
        User user = this.userService.getUserByUsername(principal.getName());
        MaintenanceSchedule maintenance = this.maintenanceScheduleService.getMaintenanceScheduleById(maintenanceScheduleId);

        Incident incident = request.getIncident();
        incident.setEmployeeId(user);

        Incident incidentAdd = this.incidentService.addOrUpdateIncident(
                incident,
                maintenance.getDeviceId().getId(),
                user
        );

        this.maintenanceScheduleService.addMaintenanceIncidentLinkByEmployee(
                user,
                maintenanceScheduleId,
                incidentAdd,
                request.getNote(),
                request.getLinkedAt()
        );

        return ResponseEntity.ok().build();
    }

}
