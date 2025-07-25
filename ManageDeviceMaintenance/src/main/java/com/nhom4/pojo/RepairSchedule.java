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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "repair_schedule")
@NamedQueries({
    @NamedQuery(name = "RepairSchedule.findAll", query = "SELECT r FROM RepairSchedule r"),
    @NamedQuery(name = "RepairSchedule.findById", query = "SELECT r FROM RepairSchedule r WHERE r.id = :id"),
    @NamedQuery(name = "RepairSchedule.findByExpectedStartDate", query = "SELECT r FROM RepairSchedule r WHERE r.expectedStartDate = :expectedStartDate"),
    @NamedQuery(name = "RepairSchedule.findByScheduleStatus", query = "SELECT r FROM RepairSchedule r WHERE r.scheduleStatus = :scheduleStatus")})
public class RepairSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "expected_start_date")
    @Temporal(TemporalType.DATE)
    private Date expectedStartDate;
    @Size(max = 20)
    @Column(name = "schedule_status")
    private String scheduleStatus;
    @OneToMany(mappedBy = "repairScheduleId")
    private Set<Repair> repairSet;
    @JoinColumn(name = "incident_id", referencedColumnName = "id")
    @ManyToOne
    private Incident incidentId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users employeeId;

    public RepairSchedule() {
    }

    public RepairSchedule(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getExpectedStartDate() {
        return expectedStartDate;
    }

    public void setExpectedStartDate(Date expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public Set<Repair> getRepairSet() {
        return repairSet;
    }

    public void setRepairSet(Set<Repair> repairSet) {
        this.repairSet = repairSet;
    }

    public Incident getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Incident incidentId) {
        this.incidentId = incidentId;
    }

    public Users getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Users employeeId) {
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
        if (!(object instanceof RepairSchedule)) {
            return false;
        }
        RepairSchedule other = (RepairSchedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.RepairSchedule[ id=" + id + " ]";
    }
    
}
