/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.configs.CustomSecurityException;
import com.nhom4.dto.DeviceDTO;
import com.nhom4.dto.RentedDeviceDTO;
import com.nhom4.dto.RentedDeviceRequest;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.Location;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.User;
import com.nhom4.services.DeviceService;
import com.nhom4.services.IncidentService;
import com.nhom4.services.RentedDeviceService;
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

    @PostMapping("/secure/devices/{id}/rented-device")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addRenedDevice(@PathVariable("id") int deviceId,
            @RequestBody RentedDeviceRequest request,
            Principal principal) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);
        RentedDevice rented = request.getRentedDevice();
        rented.setCustomerId(user);

        Location location = request.getLocation();
        rentedDeviceService.addRentedDevice(deviceId, rented, location);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("{\"error\": \"" + "Tạo phiếu mượn thành công" + "\"}");
    }

    @GetMapping("/secure/my-rented-devices")
    public ResponseEntity<List<RentedDeviceDTO>> retrieveMyRentedDevices(Principal principal) {
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);

        List<RentedDeviceDTO> rentedDeviceDTO = this.rentedDeviceService.getMyRentedDevice(user);
        return new ResponseEntity<>(rentedDeviceDTO, HttpStatus.OK);
    }

    @GetMapping("/secure/my-rented-devices/{id}/detail-device")
    public ResponseEntity<RentedDevice> getMyRentedDeviceById(@PathVariable("id") int rentedDeviceId, Principal principal) {
        System.out.printf("principal", principal);
        String username = principal.getName();
        User user = this.userService.getUserByUsername(username);
        RentedDevice rentedDevice = this.rentedDeviceService.getRentedDeviceById(user, rentedDeviceId);
        return new ResponseEntity<>(rentedDevice, HttpStatus.OK);
    }

    @PostMapping("/secure/my-rented-devices/{id}/add-report")
    public ResponseEntity<?> addIncident(
            @PathVariable("id") int deviceId,
            @RequestBody Incident incident,
            Principal principal) {

        try {
            // 1. Xác thực người dùng
            String username = principal.getName();
            User user = this.userService.getUserByUsername(username);

            // 2. Kiểm tra quyền truy cập thiết bị
            if (!rentedDeviceService.checkDeviceOwnership(deviceId, user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Bạn không có quyền truy cập thiết bị này"));
            }

            // 3. Xử lý logic nghiệp vụ
            Incident incidentSave = incidentService.addOrUpdateIncident(incident, deviceId, user);

            // 4. Trả về kết quả
            return new ResponseEntity<>(incidentSave, HttpStatus.CREATED);

        } catch (CustomSecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Không tìm thấy thiết bị"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e));
        }
    }
}
