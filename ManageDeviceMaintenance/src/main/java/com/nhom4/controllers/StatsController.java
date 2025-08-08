/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.nhom4.repositories.StatsRepository;
import com.nhom4.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class StatsController {

    @Autowired
    private StatsRepository statsRepository;

    @GetMapping("/stats")
    public String statsPage(Model model) {
        model.addAttribute("incidentStats", statsRepository.statsRevenueByIncident());
        model.addAttribute("maintenanceStats", statsRepository.statsRevenueByMaintenanceSchedule());
        model.addAttribute("maintenanceIncidentStats", statsRepository.statsRevenueByMaintenanceScheduleHaveIncident());
        return "stats";
    }
}
