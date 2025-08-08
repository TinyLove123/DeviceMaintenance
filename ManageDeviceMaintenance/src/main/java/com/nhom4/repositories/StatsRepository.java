/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom4.repositories;

import java.util.List;

/**
 *
 * @author Administrator
 */
public interface StatsRepository {
    List<Object[]> statsRevenueByIncident();
    List<Object[]> statsRevenueByMaintenanceSchedule();
    List<Object[]> statsRevenueByMaintenanceScheduleHaveIncident();
    List<Object[]> statsRevenueByTime(String time, int year);
}
