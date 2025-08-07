/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.dto.RepairDetailDTO;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.Repair;
import com.nhom4.pojo.RepairCost;
import com.nhom4.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface RepairRepository {

    Repair getRepairByIncident(int incidentId);

    void addOrUpdateRepairByIncidentId(Repair repair, Incident incident, User employee, Map<String, String> params);

    void addRepairDetail(Repair repair, List<RepairDetailDTO> repairDetailList);

}
