/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.dto.RepairDetailDTO;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.Repair;
import com.nhom4.pojo.User;
import com.nhom4.repositories.RepairRepository;
import com.nhom4.services.RepairService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class RepairServiceImpl implements RepairService{
    
    @Autowired
    private RepairRepository repairRepo;

    @Override
    public Repair getRepairByIncident(int incidentId) {
        return this.repairRepo.getRepairByIncident(incidentId);
    }

    @Override
    public void addOrUpdateRepairByIncidentId(Repair repair, Incident incident, User employee, Map<String, String> params) {
        this.repairRepo.addOrUpdateRepairByIncidentId(repair, incident, employee, params);
    }

    @Override
    public void addRepairDetail(Repair repair, List<RepairDetailDTO> repairDetailList) {
        this.repairRepo.addRepairDetail(repair, repairDetailList);
    }
    
}
