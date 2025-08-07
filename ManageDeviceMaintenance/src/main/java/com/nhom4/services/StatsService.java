/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom4.services;

import com.nhom4.dto.StatsDTO;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface StatsService {

    List<StatsDTO> statsRevenueByDevice(Integer deviceId);

    List<Object[]> statsRevenueByTime(String time, int year);
}
