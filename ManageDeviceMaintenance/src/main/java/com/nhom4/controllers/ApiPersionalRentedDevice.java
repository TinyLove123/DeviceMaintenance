/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.configs.CustomSecurityException;
import com.nhom4.dto.DeviceDTO;
import com.nhom4.dto.IncidentDTO;
import com.nhom4.dto.RentedDeviceDTO;
import com.nhom4.dto.RentedDeviceRequestDTO;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.Location;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.Repair;
import com.nhom4.pojo.User;
import com.nhom4.services.DeviceService;
import com.nhom4.services.IncidentService;
import com.nhom4.services.RentedDeviceService;
import com.nhom4.services.RepairService;
import com.nhom4.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiPersionalRentedDevice {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RentedDeviceService rentedDeviceService;

    @Autowired
    private UserService userService;

    @Autowired
    private IncidentService incidentService;
    
    @Autowired
    private RepairService repairService;

    @PostMapping("/secure/devices/{id}/rented-device")
    public ResponseEntity<String> addRentedDevice(@PathVariable("id") int deviceId,
            @RequestBody RentedDeviceRequestDTO request,
            Principal principal) {
        try {
            User user = this.userService.getUserByUsername(principal.getName());
            RentedDevice rented = request.getRentedDevice();
            rented.setCustomerId(user);

            Location loc = request.getLocation();
            rentedDeviceService.addOrUpdateRentedDevice(deviceId, rented, loc);

            return ResponseEntity.status(HttpStatus.CREATED).body("Thiết bị đã được thuê thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Thuê thiết bị thất bại.");
        }
    }

    @GetMapping("/secure/my-rented-devices")
    public ResponseEntity<List<RentedDeviceDTO>> retrieveMyRentedDevices(Principal principal) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        List<RentedDeviceDTO> rentedDeviceDTO = this.rentedDeviceService.getMyRentedDevice(user);
        return new ResponseEntity<>(rentedDeviceDTO, HttpStatus.OK);
    }
    
    

    @GetMapping("/secure/my-rented-devices/{id}/detail-device")
    public ResponseEntity<?> getMyRentedDeviceById(@PathVariable("id") int rentedDeviceId, Principal principal) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        if (!rentedDeviceService.checkDeviceOwnership(this.rentedDeviceService.getRentedDeviceById(user, rentedDeviceId).getDeviceId().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Bạn không có quyền truy cập thiết bị này"));
        }
        RentedDevice rentedDevice = this.rentedDeviceService.getRentedDeviceById(user, rentedDeviceId);
        return new ResponseEntity<>(rentedDevice, HttpStatus.OK);
    }

    @PostMapping("/secure/my-rented-devices/{id}/add-report")
    public ResponseEntity<?> addIncident(
            @PathVariable("id") int deviceId,
            @RequestBody Incident incident,
            Principal principal) {

        try {
            String username = principal.getName();
            User user = this.userService.getUserByUsername(username);

            if (!rentedDeviceService.checkDeviceOwnership(deviceId, user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Bạn không có quyền truy cập thiết bị này"));
            }

            Incident incidentSave = incidentService.addOrUpdateIncident(incident, deviceId, user);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Báo cáo sự cố thành công, chờ xét duyệt"));
        } catch (CustomSecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Không tìm thấy thiết bị"));
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Lỗi hệ thống"));
        }
    }
    
    @GetMapping("/secure/my-incident")
    public ResponseEntity<List<IncidentDTO>> getMyIncidentReport(Principal principal) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        List<IncidentDTO> incidentDTO = this.incidentService.getMyIncidentReport(user);
        return new ResponseEntity<>(incidentDTO, HttpStatus.OK);
    }
    
    @GetMapping("/secure/my-incident/{id}/detail")
     public ResponseEntity<?> getRepair( @PathVariable("id") int incidentId,Principal principal) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);
        Repair repair = this.repairService.getRepairByIncident(incidentId);
        return new ResponseEntity<>(repair, HttpStatus.OK);
    }
}
