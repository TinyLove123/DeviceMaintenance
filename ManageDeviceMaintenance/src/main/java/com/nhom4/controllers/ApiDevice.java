/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.dto.DeviceDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.Location;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.services.DeviceService;
import com.nhom4.services.IncidentService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiDevice {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private IncidentService incidentService;

    @GetMapping("/devices")
    public ResponseEntity<List<DeviceDTO>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.deviceService.getDeviceDTO(params), HttpStatus.OK);
    }

    
    @GetMapping("/devices/{deviceId}")
    public ResponseEntity<Device> retrieve(@PathVariable(value = "deviceId") int id) {
        return new ResponseEntity<>(this.deviceService.getDeviceById(id), HttpStatus.OK);
    }

    @PostMapping("/secure/devices/{id}/rented-device")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RentedDevice> addRenedDevice(@PathVariable("id") int deviceId,
            @RequestBody RentedDevice rented,
            Location loc) {
        RentedDevice rentedSave = deviceService.addRentedDevice(deviceId, rented);
        
        return new ResponseEntity<>(rentedSave, HttpStatus.CREATED);
    }
    
    @PostMapping("/secure/rented-device/{id}/add-report")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Incident> addIncident(@PathVariable("id") int deviceId,
            @RequestBody Incident incident){
        Incident incidentSave = incidentService.addOrUpdateIncident(incident, deviceId);
         return new ResponseEntity<>(incidentSave, HttpStatus.CREATED);
        
    }
    
}
