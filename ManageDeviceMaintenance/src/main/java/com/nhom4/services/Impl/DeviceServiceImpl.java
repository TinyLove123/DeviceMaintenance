/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhom4.dto.DeviceDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.RepairCost;
import com.nhom4.repositories.DeviceRepository;
import com.nhom4.services.DeviceService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private Cloudinary cloudinary;

    

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
        if (!d.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(d.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                d.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(DeviceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.deviceRepository.addOrUpdateDevice(d);
        return d;
    }

    @Override
    public void deleteDevice(int id) {
        this.deviceRepository.deleteDevice(id);
    }

    @Override
    public List<RepairCost> getRepairCostByDeviceId(int id) {
        return deviceRepository.getRepairCostByDeviceId(id);
    }

    @Override
    public RepairCost addOrUpdateRepairCost(RepairCost repairCost) {
        return this.deviceRepository.addOrUpdateRepairCost(repairCost);
    }

//    @Override
//    public List<Device> getDevice(Map<String, String> params) {
//        return List<Device> devices = deviceRepository.getDevice(params);
//        
//    }
    
//    @Override
//    public DeviceDTO getDeviceDIOById(int id) {
//
//        Device device= deviceRepository.getDeviceById(id);
//        return toDTO(device);
//    }
    
    

    @Override
    public List<Device> getDevicesByCatesId(int id, Map<String, String> params) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RepairCost getRepairCostById(int repairCostId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteRepairCost(int repairId) {
        this.deviceRepository.deleteRepairCost(repairId);
    }

    

    
    

}
