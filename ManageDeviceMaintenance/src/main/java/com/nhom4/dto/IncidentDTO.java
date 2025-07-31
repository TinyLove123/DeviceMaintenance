/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

import com.nhom4.pojo.User;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class IncidentDTO {
    private Integer id;
    private String title;
    private String detailDescribe;
    private Date reportDate;
    private DeviceOutputDTO device;
    private User sender;
    

    // ... constructor, getter/setter

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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the detailDescribe
     */
    public String getDetailDescribe() {
        return detailDescribe;
    }

    /**
     * @param detailDescribe the detailDescribe to set
     */
    public void setDetailDescribe(String detailDescribe) {
        this.detailDescribe = detailDescribe;
    }

    /**
     * @return the reportDate
     */
    public Date getReportDate() {
        return reportDate;
    }

    /**
     * @param reportDate the reportDate to set
     */
    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * @return the device
     */
    public DeviceOutputDTO getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(DeviceOutputDTO device) {
        this.device = device;
    }

    /**
     * @return the sender
     */
    public User getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(User sender) {
        this.sender = sender;
    }
}
