/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.Incident;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface IncidentRepository {
    List<Incident> getIncident(Map<String,String> params);
    Incident getIncidentById(int id);
    Incident addIncident(Incident I);
    void deleteIncident(int id);
    
}
