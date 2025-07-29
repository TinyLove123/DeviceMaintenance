/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.pojo.Location;
import com.nhom4.pojo.Provinces;
import com.nhom4.pojo.Wards;
import com.nhom4.repositories.LocationRepository;
import com.nhom4.services.LocationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService{
    @Autowired
    private LocationRepository locationRepo;

    @Override
    public List<Provinces> getProvince() {
        return this.locationRepo.getProvince();
    }

    @Override
    public List<Wards> getWard(String provinceId) {
        return  this.locationRepo.getWard(provinceId);
    }

    @Override
    public List<Location> getLocationByDeviceId(int deviceId) {
        return this.locationRepo.getLocationByDeviceId(deviceId);
    }

    @Override
    public void addLocation(int deviceId,Location newLocation) {
        this.locationRepo.addLocation(deviceId, newLocation);
    }
    
}
