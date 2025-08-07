/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

/**
 *
 * @author Administrator
 */
public class RepairDetailDTO {
    private int repairCostId;
    private String description;

    public RepairDetailDTO() {
    }

    public RepairDetailDTO(int repairCostId, String description) {
        this.repairCostId = repairCostId;
        this.description = description;
    }

    /**
     * @return the repairCostId
     */
    public int getRepairCostId() {
        return repairCostId;
    }

    /**
     * @param repairCostId the repairCostId to set
     */
    public void setRepairCostId(int repairCostId) {
        this.repairCostId = repairCostId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
