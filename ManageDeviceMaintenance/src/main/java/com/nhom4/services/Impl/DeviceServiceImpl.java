/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.RepairCost;
import com.nhom4.repositories.DeviceRepository;
import com.nhom4.services.DeviceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Administrator
 */
@Service
public class DeviceServiceImpl implements DeviceService{
    
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Device> getDevice(Map<String, String> params) {
        return deviceRepository.getDevice(params);
    }

    @Override
    public Device getDeviceById(int id) {
         return deviceRepository.getDeviceById(id);
    }

    @Override
    public Device addOrUpdateDevice(Device d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   

    @Override
    public void deleteDevice(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<RepairCost> getRepairType(int id) {
        return deviceRepository.getRepairType(id);
    }

    @Override
    public RepairCost addOrUpdateRepairCost(RepairCost repairCost) {
        return this.deviceRepository.addOrUpdateRepairCost(repairCost);
    }


    
    
}
