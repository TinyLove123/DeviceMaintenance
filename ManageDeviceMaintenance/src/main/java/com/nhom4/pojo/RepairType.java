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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "repair_type")
@NamedQueries({
    @NamedQuery(name = "RepairType.findAll", query = "SELECT r FROM RepairType r"),
    @NamedQuery(name = "RepairType.findById", query = "SELECT r FROM RepairType r WHERE r.id = :id"),
    @NamedQuery(name = "RepairType.findByType", query = "SELECT r FROM RepairType r WHERE r.type = :type")})
public class RepairType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "type")
    private String type;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repairTypeId")
    private Set<RepairCost> repairCostSet;

    public RepairType() {
    }

    public RepairType(Integer id) {
        this.id = id;
    }

    public RepairType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<RepairCost> getRepairCostSet() {
        return repairCostSet;
    }

    public void setRepairCostSet(Set<RepairCost> repairCostSet) {
        this.repairCostSet = repairCostSet;
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
        if (!(object instanceof RepairType)) {
            return false;
        }
        RepairType other = (RepairType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.RepairType[ id=" + id + " ]";
    }
    
}
