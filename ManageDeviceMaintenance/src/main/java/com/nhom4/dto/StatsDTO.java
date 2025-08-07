/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class StatsDTO {

    private String type; // "maintenance" or "incident"
    private Integer id;
    private Date date;
    private String description;

    public StatsDTO(String type, Integer id, Date date, String description) {
        this.type = type;
        this.id = id;
        this.date = date;
        this.description = description;
    }

    // Getters và setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
