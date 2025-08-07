package com.nhom4.dto;

import com.nhom4.pojo.User;
import java.util.Date;

public class IncidentDTO {

    private Integer id;
    private String title;
    private String detailDescribe;
    private String status;
    private Date reportDate;
    private Date approvalDate;
    private Boolean isEmergency;
    private Date startDate;
    private Date endDate;
    private User approvedBy;
    private User employee;
    private User sender;
    private String receptStatus;

    private DeviceDTO device;

    public IncidentDTO() {
    }

    public IncidentDTO(Integer id, String title, String detailDescribe, String status,
            Date reportDate, Date approvalDate, Boolean isEmergency,
            Date startDate, Date endDate,
            User approvedBy, User employee, User sender, DeviceDTO device) {
        this.id = id;
        this.title = title;
        this.detailDescribe = detailDescribe;
        this.status = status;
        this.reportDate = reportDate;
        this.approvalDate = approvalDate;
        this.isEmergency = isEmergency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approvedBy = approvedBy;
        this.employee = employee;
        this.sender = sender;
        this.device = device;
        this.receptStatus = device.getStatusDevice();
    }
//    public IncidentDTO(Integer id, String title, String detailDescribe, String status,
//            Date reportDate, Date approvalDate, Boolean isEmergency,
//            Date startDate, Date endDate,
//            User approvedBy, User employee, User sender) {
//        this.id = id;
//        this.title = title;
//        this.detailDescribe = detailDescribe;
//        this.status = status;
//        this.reportDate = reportDate;
//        this.approvalDate = approvalDate;
//        this.isEmergency = isEmergency;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.approvedBy = approvedBy;
//        this.employee = employee;
//        this.sender = sender;
//        this.device = device;
//        this.receptStatus = device.getStatusDevice();
//    }

    // --- Getters & Setters ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailDescribe() {
        return detailDescribe;
    }

    public void setDetailDescribe(String detailDescribe) {
        this.detailDescribe = detailDescribe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Boolean getIsEmergency() {
        return isEmergency;
    }

    public void setIsEmergency(Boolean isEmergency) {
        this.isEmergency = isEmergency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
    
    public DeviceDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }

    /**
     * @return the receptStatus
     */
    public String getReceptStatus() {
        return receptStatus;
    }

    /**
     * @param receptStatus the receptStatus to set
     */
    public void setReceptStatus(String receptStatus) {
        this.receptStatus = receptStatus;
    }
}
