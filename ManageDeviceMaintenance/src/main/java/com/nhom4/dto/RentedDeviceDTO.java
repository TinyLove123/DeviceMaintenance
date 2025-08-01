/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class RentedDeviceDTO {
    private int id;
    private Date startDate;
    private Date endDate;
    private Boolean isRented;
    private String deviceName;
    private String manufacturer;

    public RentedDeviceDTO(int id, Date startDate, Date endDate, Boolean isRented, String deviceName, String manufacturer) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isRented = isRented;
        this.deviceName = deviceName;
        this.manufacturer = manufacturer;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the isRented
     */
    public Boolean getIsRented() {
        return isRented;
    }

    /**
     * @param isRented the isRented to set
     */
    public void setIsRented(Boolean isRented) {
        this.isRented = isRented;
    }

    /**
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName the deviceName to set
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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

    
}
