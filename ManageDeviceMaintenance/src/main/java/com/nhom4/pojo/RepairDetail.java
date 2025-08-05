/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "repair_detail")
@NamedQueries({
    @NamedQuery(name = "RepairDetail.findAll", query = "SELECT r FROM RepairDetail r"),
    @NamedQuery(name = "RepairDetail.findById", query = "SELECT r FROM RepairDetail r WHERE r.id = :id")})
public class RepairDetail implements Serializable {

    @Lob
    @Size(max = 65535)
    @Column(name = "description_detail")
    private String descriptionDetail;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "repair_id", referencedColumnName = "id")
    @ManyToOne
    private Repair repairId;
    @JoinColumn(name = "repair_cost_id", referencedColumnName = "id")
    @ManyToOne
    private RepairCost repairCostId;

    public RepairDetail() {
    }

    public RepairDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescriptionDetail() {
        return descriptionDetail;
    }

    public void setDescriptionDetail(String descriptionDetail) {
        this.descriptionDetail = descriptionDetail;
    }

    public Repair getRepairId() {
        return repairId;
    }

    public void setRepairId(Repair repairId) {
        this.repairId = repairId;
    }

    public RepairCost getRepairCostId() {
        return repairCostId;
    }

    public void setRepairCostId(RepairCost repairCostId) {
        this.repairCostId = repairCostId;
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
        if (!(object instanceof RepairDetail)) {
            return false;
        }
        RepairDetail other = (RepairDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.RepairDetail[ id=" + id + " ]";
    }

    
    
}
