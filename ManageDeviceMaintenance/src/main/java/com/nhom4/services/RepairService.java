/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services;

import com.nhom4.dto.RepairDetailDTO;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.Repair;
import com.nhom4.pojo.RepairDetail;
import com.nhom4.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface RepairService {

    Repair getRepairByIncident(int incidentId);

    void addOrUpdateRepairByIncidentId(Repair repair, Incident incident, User employee, Map<String, String> params);

    void addRepairDetail(Repair repair, List<RepairDetailDTO> repairDetailList);
    
    void deleteRepairDetail(int repairDetailId);
    
//    RepairDetail getRepairDetailById(int repairDetailId);
}
