/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.Location;
import com.nhom4.pojo.Provinces;
import com.nhom4.pojo.Wards;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface LocationRepository {
    
    
    List<Provinces> getProvince();

    List<Wards> getWard(String provinceId);
    
    List<Location> getLocationByDeviceId(int deviceId);
    
    void addLocation(int deviceId, Location newLocation);
    
    Location getLocationById(int Location);
}
