/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

import com.nhom4.pojo.Incident;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class AddIncidentLinkRequestDTO {
     private Incident incident;
    private String note;
    private Date linkedAt;

    public AddIncidentLinkRequestDTO() {
    }

    public AddIncidentLinkRequestDTO(Incident incident, String note, Date linkedAt) {
        this.incident = incident;
        this.note = note;
        this.linkedAt = linkedAt;
    }
    
    

    /**
     * @return the incident
     */
    public Incident getIncident() {
        return incident;
    }

    /**
     * @param incident the incident to set
     */
    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the linkedAt
     */
    public Date getLinkedAt() {
        return linkedAt;
    }

    /**
     * @param linkedAt the linkedAt to set
     */
    public void setLinkedAt(Date linkedAt) {
        this.linkedAt = linkedAt;
    }
    
}
