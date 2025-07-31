/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services;

import java.util.List;
import java.util.Map;

import com.nhom4.dto.DeviceDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.RepairCost;

/**
 *
 * @author Administrator
 */
public interface DeviceService {


    List<Device> getDevice(Map<String,String> params);
    Device getDeviceById(int id);

    Device addOrUpdateDevice(Device d);

    List<Device> getDevicesByCatesId(int id, Map<String, String> params);

    void deleteDevice(int deviceId);

    List<RepairCost> getRepairTypeByDeviceId(int deviceId);

    RepairCost addOrUpdateRepairCost(RepairCost repairCost);

    RepairCost getRepairCostById(int repairCostId);

    void deleteRepairCost(int repairId);
    
    
    
    public List<DeviceDTO> getDeviceDTO(Map<String, String> params);

    public DeviceDTO getDeviceDIOById(int id);

}
