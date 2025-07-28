/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.RepairCost;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */

public interface DeviceService {
    List<Device> getDevice(Map<String,String> params);
    Device getDeviceById(int id);
    Device addOrUpdateDevice(Device d);
    void deleteDevice(int id);
    List<RepairCost> getRepairType(int id);
    RepairCost addOrUpdateRepairCost(RepairCost repairCost);
}
