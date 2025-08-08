/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhom4.dto.DeviceDTO;
import com.nhom4.pojo.Category;
import com.nhom4.pojo.Device;
import com.nhom4.services.CategoryService;
import com.nhom4.services.DeviceService;
import com.nhom4.services.IncidentService;
import com.nhom4.services.UserService;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiDevice {

    @Autowired
    private CategoryService catService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private UserService userService;

    @GetMapping("/devices")
    public ResponseEntity<List<DeviceDTO>> listDevs(@RequestParam Map<String, String> params) {
        List<Device> devices = deviceService.getDevice(params);
        List<DeviceDTO> dtos = devices.stream()
                              .map(DeviceDTO::new)
                              .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    
    
    
    @GetMapping("/secure/categories")
    public ResponseEntity<List<Category>> listCats() {
        return new ResponseEntity<>(this.catService.getCates(), HttpStatus.OK);
    }

    @GetMapping("/devices/{deviceId}")
    public ResponseEntity<Device> retrieve(@PathVariable(value = "deviceId") int id) {
        return new ResponseEntity<>(this.deviceService.getDeviceById(id), HttpStatus.OK);
    }


}
