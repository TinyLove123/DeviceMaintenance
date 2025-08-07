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
@Table(name = "maintenance_schedule")
@NamedQueries({
    @NamedQuery(name = "MaintenanceSchedule.findAll", query = "SELECT m FROM MaintenanceSchedule m"),
    @NamedQuery(name = "MaintenanceSchedule.findById", query = "SELECT m FROM MaintenanceSchedule m WHERE m.id = :id"),
    @NamedQuery(name = "MaintenanceSchedule.findByStartDate", query = "SELECT m FROM MaintenanceSchedule m WHERE m.startDate = :startDate"),
    @NamedQuery(name = "MaintenanceSchedule.findByProgress", query = "SELECT m FROM MaintenanceSchedule m WHERE m.progress = :progress")})
public class MaintenanceSchedule implements Serializable {

    @Size(max = 20)
    @Column(name = "progress")
    private String progress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "recept_status")
    private String receptStatus;
    @Column(name = "is_auto_add")
    private Boolean isAutoAdd;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "maintenanceScheduleId")
    private MaintenanceReport maintenanceScheduleReport;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maintenanceScheduleId")
    private Set<MaintenanceReport> maintenanceScheduleReportSet;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "maintenanceScheduleId")
    private MaintenanceIncidentLink maintenanceIncidentLink;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Device deviceId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne
    private User employeeId;

    public MaintenanceSchedule() {
    }

    public MaintenanceSchedule(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Device getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Device deviceId) {
        this.deviceId = deviceId;
    }

    public User getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(User employeeId) {
        this.employeeId = employeeId;
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
        if (!(object instanceof MaintenanceSchedule)) {
            return false;
        }
        MaintenanceSchedule other = (MaintenanceSchedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.MaintenanceSchedule[ id=" + id + " ]";
    }


    public Set<MaintenanceReport> getMaintenanceScheduleReportSet() {
        return maintenanceScheduleReportSet;
    }

    public void setMaintenanceScheduleReportSet(Set<MaintenanceReport> maintenanceScheduleReportSet) {
        this.maintenanceScheduleReportSet = maintenanceScheduleReportSet;
    }

    public MaintenanceIncidentLink getMaintenanceIncidentLink() {
        return maintenanceIncidentLink;
    }

    public void setMaintenanceIncidentLink(MaintenanceIncidentLink maintenanceIncidentLink) {
        this.maintenanceIncidentLink = maintenanceIncidentLink;
    }


    public String getReceptStatus() {
        return receptStatus;
    }

    public void setReceptStatus(String receptStatus) {
        this.receptStatus = receptStatus;
    }


    

    public MaintenanceReport getMaintenanceScheduleReport() {
        return maintenanceScheduleReport;
    }

    public void setMaintenanceScheduleReport(MaintenanceReport maintenanceScheduleReport) {
        this.maintenanceScheduleReport = maintenanceScheduleReport;
    }


   

    public Boolean getIsAutoAdd() {
        return isAutoAdd;
    }

    public void setIsAutoAdd(Boolean isAutoAdd) {
        this.isAutoAdd = isAutoAdd;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

   

    
   
    
}
