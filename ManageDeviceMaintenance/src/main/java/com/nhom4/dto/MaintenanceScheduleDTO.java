package com.nhom4.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MaintenanceScheduleDTO {
    private Integer id;
    private String startDate;
    private String progress;

    private Integer deviceId;
    private String deviceName;

    private Integer employeeId;
    private String employeeName;

    public MaintenanceScheduleDTO() {
    }

    public MaintenanceScheduleDTO(Integer id, Date startDate, String progress,
                                   Integer deviceId, String deviceName,
                                   Integer employeeId, String employeeName) {
        this.id = id;
        this.startDate = formatDate(startDate);
        this.progress = progress;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    private String formatDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }

    // Getters & setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = formatDate(startDate);
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
