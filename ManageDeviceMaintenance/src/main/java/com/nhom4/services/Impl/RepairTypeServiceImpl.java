/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.pojo.RepairType;
import com.nhom4.repositories.DeviceRepository;
import com.nhom4.repositories.RepairTypeRepository;
import com.nhom4.services.RepairTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class RepairTypeServiceImpl implements RepairTypeService{
    
    @Autowired
    private RepairTypeRepository repairTypeRepository;

    @Override
    public List<RepairType> getRepairType() {
       return repairTypeRepository.getRepairType();
    }

    @Override
    public RepairType addRepairType(RepairType RId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RepairType getRepairTypeById(int RId) {
        return repairTypeRepository.getRepairTypeById(RId);
    }
    
}
