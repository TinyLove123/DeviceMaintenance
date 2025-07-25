/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.RepairCost;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface DeviceRepository {
    List<Device> getDevice(Map<String,String> params);
    Device getDeviceById(int id);
    Device addOrUpdateDevice(Device d);
    List<Device> getDevicesByCatesId(int id, Map<String,String> params);
    void deleteDevice(int id);
    List<RepairCost> getRepairType(int id);
    void updateRepairType(int id,Map<String,String>params);
    RepairCost addOrUpdateRepairCost(RepairCost repairCost);

}
