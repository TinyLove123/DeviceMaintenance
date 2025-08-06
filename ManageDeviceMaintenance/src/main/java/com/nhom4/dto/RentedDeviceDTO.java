/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

import com.nhom4.pojo.Location;
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
    private int deviceId;
    private String deviceName;
    private String manufacturer;
    private String image;

    public RentedDeviceDTO() {
    }
    public RentedDeviceDTO(int id, Date startDate, Date endDate, Boolean isRented, String deviceName, String manufacturer, String image) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.isRented = isRented;
    this.deviceName = deviceName;
    this.manufacturer = manufacturer;
    this.image = image;
}
    

    public RentedDeviceDTO(int id, Date startDate, Date endDate, Boolean isRented,int deviceId, String deviceName, String manufacturer,String image) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isRented = isRented;
        this.deviceId=deviceId;
        this.deviceName = deviceName;
        this.manufacturer = manufacturer;
        this.image = image;
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

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

   

    /**
     * @return the deviceId
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    
}
