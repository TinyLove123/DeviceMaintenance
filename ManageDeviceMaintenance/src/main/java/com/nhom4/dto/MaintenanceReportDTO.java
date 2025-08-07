/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class MaintenanceReportDTO {
    private Integer id;
    private String description;
    private double price;
    private String maintenanceRate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date reportDate;
    // thêm nếu cần
    
    public MaintenanceReportDTO() {
    }

    public MaintenanceReportDTO(Integer id, String description, double price,
                                   String maintenanceRate, Date reportDate) {
        this.id = id;
        this.description=description;
        this.price=price;
        this.maintenanceRate=maintenanceRate;
        this.reportDate = reportDate;
       
    }

    private String formatDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }

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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the maintenanceRate
     */
    public String getMaintenanceRate() {
        return maintenanceRate;
    }

    /**
     * @param maintenanceRate the maintenanceRate to set
     */
    public void setMaintenanceRate(String maintenanceRate) {
        this.maintenanceRate = maintenanceRate;
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
     * @return the reportDate
     */
   
}
