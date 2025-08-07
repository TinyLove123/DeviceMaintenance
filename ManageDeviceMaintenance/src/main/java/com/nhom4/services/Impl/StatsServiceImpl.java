/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

import com.nhom4.dto.StatsDTO;
import com.nhom4.repositories.StatsRepository;
import com.nhom4.services.StatsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class StatsServiceImpl implements StatsService{
    
    @Autowired
    private StatsRepository statsRepo;
    
    @Override
    public List<StatsDTO> statsRevenueByDevice(Integer deviceId) {
        return this.statsRepo.statsRevenueByDevice(deviceId);
    }

    @Override
    public List<Object[]> statsRevenueByTime(String time, int year) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
