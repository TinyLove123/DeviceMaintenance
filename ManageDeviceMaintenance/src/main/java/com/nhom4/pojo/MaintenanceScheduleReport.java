/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.pojo;

import jakarta.persistence.Basic;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "maintenance_schedule_report")
@NamedQueries({
    @NamedQuery(name = "MaintenanceScheduleReport.findAll", query = "SELECT m FROM MaintenanceScheduleReport m"),
    @NamedQuery(name = "MaintenanceScheduleReport.findById", query = "SELECT m FROM MaintenanceScheduleReport m WHERE m.id = :id"),
    @NamedQuery(name = "MaintenanceScheduleReport.findByReportDate", query = "SELECT m FROM MaintenanceScheduleReport m WHERE m.reportDate = :reportDate"),
    @NamedQuery(name = "MaintenanceScheduleReport.findByPrice", query = "SELECT m FROM MaintenanceScheduleReport m WHERE m.price = :price"),
    @NamedQuery(name = "MaintenanceScheduleReport.findByMaintenanceRate", query = "SELECT m FROM MaintenanceScheduleReport m WHERE m.maintenanceRate = :maintenanceRate")})
public class MaintenanceScheduleReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "report_date")
    @Temporal(TemporalType.DATE)
    private Date reportDate;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private double price;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "maintenance_rate")
    private String maintenanceRate;
    @JoinColumn(name = "maintenance_schedule_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private MaintenanceSchedule maintenanceScheduleId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User employeeId;

    public MaintenanceScheduleReport() {
    }

    public MaintenanceScheduleReport(Integer id) {
        this.id = id;
    }

    public MaintenanceScheduleReport(Integer id, String description, double price, String maintenanceRate) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.maintenanceRate = maintenanceRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMaintenanceRate() {
        return maintenanceRate;
    }

    public void setMaintenanceRate(String maintenanceRate) {
        this.maintenanceRate = maintenanceRate;
    }

    public MaintenanceSchedule getMaintenanceScheduleId() {
        return maintenanceScheduleId;
    }

    public void setMaintenanceScheduleId(MaintenanceSchedule maintenanceScheduleId) {
        this.maintenanceScheduleId = maintenanceScheduleId;
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
        if (!(object instanceof MaintenanceScheduleReport)) {
            return false;
        }
        MaintenanceScheduleReport other = (MaintenanceScheduleReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.MaintenanceScheduleReport[ id=" + id + " ]";
    }
    
}
