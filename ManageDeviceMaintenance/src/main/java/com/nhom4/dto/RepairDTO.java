/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

import java.util.List;

/**
 *
 * @author Administrator
 */
public class RepairDTO {
     private Integer id;
    private List<RepairDetailDTO> repairDetails;

    public RepairDTO() {
    }

    public RepairDTO(Integer id, List<RepairDetailDTO> repairDetails) {
        this.id = id;
        this.repairDetails = repairDetails;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the repairDetails
     */
    public List<RepairDetailDTO> getRepairDetails() {
        return repairDetails;
    }

    /**
     * @param repairDetails the repairDetails to set
     */
    public void setRepairDetails(List<RepairDetailDTO> repairDetails) {
        this.repairDetails = repairDetails;
    }
    
    
    
}
