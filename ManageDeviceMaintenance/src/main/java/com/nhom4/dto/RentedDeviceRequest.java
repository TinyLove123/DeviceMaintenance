/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

import com.nhom4.pojo.Location;
import com.nhom4.pojo.RentedDevice;

/**
 *
 * @author Administrator
 */
public class RentedDeviceRequest {
    private RentedDevice rentedDevice;
    private Location location;

    /**
     * @return the rentedDevice
     */
    public RentedDevice getRentedDevice() {
        return rentedDevice;
    }

    /**
     * @param rentedDevice the rentedDevice to set
     */
    public void setRentedDevice(RentedDevice rentedDevice) {
        this.rentedDevice = rentedDevice;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    

}
