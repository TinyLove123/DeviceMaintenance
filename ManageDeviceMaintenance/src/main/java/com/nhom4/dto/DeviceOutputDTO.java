/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

/**
 *
 * @author Administrator
 */
public class DeviceOutputDTO {
    private Integer id;
    private String nameDevice;
    private String manufacturer;
    private String statusDevice;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nameDevice
     */
    public String getNameDevice() {
        return nameDevice;
    }

    /**
     * @param nameDevice the nameDevice to set
     */
    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the statusDevice
     */
    public String getStatusDevice() {
        return statusDevice;
    }

    /**
     * @param statusDevice the statusDevice to set
     */
    public void setStatusDevice(String statusDevice) {
        this.statusDevice = statusDevice;
    }
    
}
