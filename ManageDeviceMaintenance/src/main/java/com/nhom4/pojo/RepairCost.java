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
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "repair_cost")
@NamedQueries({
    @NamedQuery(name = "RepairCost.findAll", query = "SELECT r FROM RepairCost r"),
    @NamedQuery(name = "RepairCost.findById", query = "SELECT r FROM RepairCost r WHERE r.id = :id"),
    @NamedQuery(name = "RepairCost.findByPrice", query = "SELECT r FROM RepairCost r WHERE r.price = :price")})
public class RepairCost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;
    @OneToMany(mappedBy = "repairCostId")
    private Set<RepairDetail> repairDetailSet;
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Device deviceId;
    @JoinColumn(name = "repair_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RepairType repairTypeId;

    public RepairCost() {
    }

    public RepairCost(Integer id) {
        this.id = id;
    }

    public RepairCost(Integer id, int price) {
        this.id = id;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<RepairDetail> getRepairDetailSet() {
        return repairDetailSet;
    }

    public void setRepairDetailSet(Set<RepairDetail> repairDetailSet) {
        this.repairDetailSet = repairDetailSet;
    }

    public Device getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Device deviceId) {
        this.deviceId = deviceId;
    }

    public RepairType getRepairTypeId() {
        return repairTypeId;
    }

    public void setRepairTypeId(RepairType repairTypeId) {
        this.repairTypeId = repairTypeId;
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
        if (!(object instanceof RepairCost)) {
            return false;
        }
        RepairCost other = (RepairCost) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.RepairCost[ id=" + id + " ]";
    }
    
}
