/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.Device;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface DeviceRepository {
    List<Device> getDevice(Map<String,String> params);
    Device getExerciseById(int id);
    Device addOrUpdateDevice(Device d);
    void deleteDevice(int id);
    
}
