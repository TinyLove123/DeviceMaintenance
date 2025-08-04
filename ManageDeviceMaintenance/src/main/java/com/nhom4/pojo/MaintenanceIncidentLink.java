/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.pojo;

import com.nhom4.pojo.Incident;
import com.nhom4.pojo.MaintenanceSchedule;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "maintenance_incident_link")
@NamedQueries({
    @NamedQuery(name = "MaintenanceIncidentLink.findAll", query = "SELECT m FROM MaintenanceIncidentLink m"),
    @NamedQuery(name = "MaintenanceIncidentLink.findById", query = "SELECT m FROM MaintenanceIncidentLink m WHERE m.id = :id"),
    @NamedQuery(name = "MaintenanceIncidentLink.findByLinkedAt", query = "SELECT m FROM MaintenanceIncidentLink m WHERE m.linkedAt = :linkedAt")})
public class MaintenanceIncidentLink implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "linked_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date linkedAt;
    @Lob
    @Size(max = 65535)
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "incident_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Incident incidentId;
    @JoinColumn(name = "maintenance_schedule_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private MaintenanceSchedule maintenanceScheduleId;

    public MaintenanceIncidentLink() {
    }

    public MaintenanceIncidentLink(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLinkedAt() {
        return linkedAt;
    }

    public void setLinkedAt(Date linkedAt) {
        this.linkedAt = linkedAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Incident getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Incident incidentId) {
        this.incidentId = incidentId;
    }

    public MaintenanceSchedule getMaintenanceScheduleId() {
        return maintenanceScheduleId;
    }

    public void setMaintenanceScheduleId(MaintenanceSchedule maintenanceScheduleId) {
        this.maintenanceScheduleId = maintenanceScheduleId;
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
        if (!(object instanceof MaintenanceIncidentLink)) {
            return false;
        }
        MaintenanceIncidentLink other = (MaintenanceIncidentLink) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.controllers.MaintenanceIncidentLink[ id=" + id + " ]";
    }
    
}
