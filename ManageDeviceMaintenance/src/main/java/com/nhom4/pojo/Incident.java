/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "incident")
@NamedQueries({
    @NamedQuery(name = "Incident.findAll", query = "SELECT i FROM Incident i"),
    @NamedQuery(name = "Incident.findById", query = "SELECT i FROM Incident i WHERE i.id = :id"),
    @NamedQuery(name = "Incident.findByTitle", query = "SELECT i FROM Incident i WHERE i.title = :title"),
    @NamedQuery(name = "Incident.findByReportDate", query = "SELECT i FROM Incident i WHERE i.reportDate = :reportDate"),
    @NamedQuery(name = "Incident.findByStatus", query = "SELECT i FROM Incident i WHERE i.status = :status"),
    @NamedQuery(name = "Incident.findByApprovalDate", query = "SELECT i FROM Incident i WHERE i.approvalDate = :approvalDate"),
    @NamedQuery(name = "Incident.findByIsEmergency", query = "SELECT i FROM Incident i WHERE i.isEmergency = :isEmergency"),
    @NamedQuery(name = "Incident.findByStartDate", query = "SELECT i FROM Incident i WHERE i.startDate = :startDate"),
    @NamedQuery(name = "Incident.findByEndDate", query = "SELECT i FROM Incident i WHERE i.endDate = :endDate"),
    @NamedQuery(name = "Incident.findByReceptStatus", query = "SELECT i FROM Incident i WHERE i.receptStatus = :receptStatus")})
public class Incident implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "detail_describe")
    private String detailDescribe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "status")
    private String status;
    @Size(max = 50)
    @Column(name = "recept_status")
    private String receptStatus;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "incidentId")
    private MaintenanceIncidentLink maintenanceIncidentLink;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "report_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportDate;
    @Column(name = "approval_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
    @Column(name = "is_emergency")
    private Boolean isEmergency;
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @JsonIgnore
    @OneToOne(mappedBy = "incidentId")
    private Repair repair;
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    @ManyToOne
    private Device deviceId;
    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    @ManyToOne
    private User approvedBy;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne
    private User employeeId;
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User senderId;

    public Incident() {
    }

    public Incident(Integer id) {
        this.id = id;
    }

    public Incident(Integer id, String title, String detailDescribe, String status) {
        this.id = id;
        this.title = title;
        this.detailDescribe = detailDescribe;
        this.status = status;
    }

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

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getReceptStatus() {
        return receptStatus;
    }

    public void setReceptStatus(String receptStatus) {
        this.receptStatus = receptStatus;
    }

    public Repair getRepair() {
        return repair;
    }

    public void setRepair(Repair repair) {
        this.repair = repair;
    }

    public Device getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Device deviceId) {
        this.deviceId = deviceId;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    public User getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(User employeeId) {
        this.employeeId = employeeId;
    }

    public User getSenderId() {
        return senderId;
    }

    public void setSenderId(User senderId) {
        this.senderId = senderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incident)) {
            return false;
        }
        Incident other = (Incident) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.Incident[ id=" + id + " ]";
    }


    public MaintenanceIncidentLink getMaintenanceIncidentLink() {
        return maintenanceIncidentLink;
    }

    public void setMaintenanceIncidentLink(MaintenanceIncidentLink maintenanceIncidentLink) {
        this.maintenanceIncidentLink = maintenanceIncidentLink;
    }


    
}
