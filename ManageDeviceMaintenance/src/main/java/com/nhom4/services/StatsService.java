/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.services;

import java.util.List;

/**
 *
 * @author Administrator
 */
public interface StatsService {

    List<Object[]> statsRevenueByIncident();

    List<Object[]> statsRevenueByMaintenanceSchedule();

    List<Object[]> statsRevenueByMaintenanceScheduleHaveIncident();

    List<Object[]> statsRevenueByTime(String time, int year);
}
