/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services.Impl;

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
    public List<Object[]> statsRevenueByIncident() {
        return this.statsRepo.statsRevenueByIncident();
    }

    @Override
    public List<Object[]> statsRevenueByMaintenanceSchedule() {
        return  this.statsRepo.statsRevenueByMaintenanceSchedule();
    }

    @Override
    public List<Object[]> statsRevenueByMaintenanceScheduleHaveIncident() {
         return  this.statsRepo.statsRevenueByMaintenanceScheduleHaveIncident();
    }

    @Override
    public List<Object[]> statsRevenueByTime(String time, int year) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
