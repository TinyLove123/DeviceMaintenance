 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.dto.RentedDeviceDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.Location;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.User;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface RentedDeviceRepository {
    RentedDevice addRentedDevice(int deviceId, RentedDevice rentedDevice,Location location);
    List<RentedDeviceDTO> getMyRentedDevice(User user);
    RentedDevice getRentedDeviceById(User user, int rentedDeviceId);
     boolean checkDeviceOwnership(int deviceId, int userId);
}
