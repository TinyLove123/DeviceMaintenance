/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.dto.RentedDeviceDTO;
import com.nhom4.pojo.Location;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.User;
import com.nhom4.repositories.RentedDeviceRepository;
import com.nhom4.services.RentedDeviceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */

@Service
public class RentedDeviceServiceImpl implements RentedDeviceService{
    
    @Autowired
    private RentedDeviceRepository rentedDeviceRepo;

    @Override
    public List<RentedDeviceDTO> getMyRentedDevice(User user) {
        return this.rentedDeviceRepo.getMyRentedDevice(user);
    }
    
    @Override
    public RentedDevice addRentedDevice(int deviceId, RentedDevice rentedDevice, Location location) {
        return this.rentedDeviceRepo.addRentedDevice(deviceId, rentedDevice, location);
    }

    @Override   
    public RentedDevice getRentedDeviceById(User user, int rentedDeviceId) {
        return this.rentedDeviceRepo.getRentedDeviceById(user, rentedDeviceId);
    }
    @Override
    public boolean checkDeviceOwnership(int deviceId, int userId) {
        return rentedDeviceRepo.checkDeviceOwnership(deviceId, userId);
    }
}
