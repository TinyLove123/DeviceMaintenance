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
@Table(name = "repair")
@NamedQueries({
    @NamedQuery(name = "Repair.findAll", query = "SELECT r FROM Repair r"),
    @NamedQuery(name = "Repair.findById", query = "SELECT r FROM Repair r WHERE r.id = :id"),
    @NamedQuery(name = "Repair.findByEndDate", query = "SELECT r FROM Repair r WHERE r.endDate = :endDate"),
    @NamedQuery(name = "Repair.findByProgress", query = "SELECT r FROM Repair r WHERE r.progress = :progress")})
public class Repair implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Size(max = 20)
    @Column(name = "progress")
    private String progress;

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "repairId")
    private Set<RepairDetail> repairDetailSet;

    
    @JoinColumn(name = "incident_id", referencedColumnName = "id")
    @JsonIgnore
    @OneToOne
    private Incident incidentId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne
    private User employeeId;

    public Repair() {
    }

    public Repair(Integer id) {
        this.id = id;
    }

    public Repair(Integer id, Date endDate) {
        this.id = id;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public Incident getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Incident incidentId) {
        this.incidentId = incidentId;
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
        if (!(object instanceof Repair)) {
            return false;
        }
        Repair other = (Repair) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.Repair[ id=" + id + " ]";
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the repairDetailSet
     */
    public Set<RepairDetail> getRepairDetailSet() {
        return repairDetailSet;
    }

    /**
     * @param repairDetailSet the repairDetailSet to set
     */
    public void setRepairDetailSet(Set<RepairDetail> repairDetailSet) {
        this.repairDetailSet = repairDetailSet;
    }

    

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
    
}
