/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.OneToMany;
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
    @NamedQuery(name = "Incident.findByReportDate", query = "SELECT i FROM Incident i WHERE i.reportDate = :reportDate")})
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @Column(name = "report_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportDate;
    @OneToMany(mappedBy = "incidentId")
    private Set<Repair> repairSet;
    @OneToMany(mappedBy = "incidentId")
    private Set<RepairSchedule> repairScheduleSet;
    @JsonIgnore
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    @ManyToOne
    private Device deviceId;
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User senderId;

    public Incident() {
    }

    public Incident(Integer id) {
        this.id = id;
    }

    public Incident(Integer id, String title, String detailDescribe) {
        this.id = id;
        this.title = title;
        this.detailDescribe = detailDescribe;
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

    public Set<Repair> getRepairSet() {
        return repairSet;
    }

    public void setRepairSet(Set<Repair> repairSet) {
        this.repairSet = repairSet;
    }

    public Set<RepairSchedule> getRepairScheduleSet() {
        return repairScheduleSet;
    }

    public void setRepairScheduleSet(Set<RepairSchedule> repairScheduleSet) {
        this.repairScheduleSet = repairScheduleSet;
    }

    public Device getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Device deviceId) {
        this.deviceId = deviceId;
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
    
}
