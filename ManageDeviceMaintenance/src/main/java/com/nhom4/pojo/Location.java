/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "location")
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l"),
    @NamedQuery(name = "Location.findById", query = "SELECT l FROM Location l WHERE l.id = :id"),
    @NamedQuery(name = "Location.findByLastUpdate", query = "SELECT l FROM Location l WHERE l.lastUpdate = :lastUpdate"),
    @NamedQuery(name = "Location.findByAddress", query = "SELECT l FROM Location l WHERE l.address = :address"),
    @NamedQuery(name = "Location.findByIsCurrentLocation", query = "SELECT l FROM Location l WHERE l.isCurrentLocation = :isCurrentLocation")})
public class Location implements Serializable {

    @Size(max = 30)
    @Column(name = "address")
    private String address;
    @OneToOne(mappedBy = "lastLocationId")
    private Location location;
    @OneToMany(mappedBy = "currentLocationId")
    private Set<Device> deviceSet;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "is_current_location")
    private Boolean isCurrentLocation;
    @JsonIgnore
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    @ManyToOne
    private Device deviceId;
    @JsonIgnore
    @JoinColumn(name = "last_location_id", referencedColumnName = "id")
    @OneToOne
    private Location lastLocationId;
    @JoinColumn(name = "ward_id", referencedColumnName = "code")
    @ManyToOne
    private Wards wardId;


    public Location() {
    }

    public Location(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    public Boolean getIsCurrentLocation() {
        return isCurrentLocation;
    }

    public void setIsCurrentLocation(Boolean isCurrentLocation) {
        this.isCurrentLocation = isCurrentLocation;
    }

    public Device getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Device deviceId) {
        this.deviceId = deviceId;
    }

    

    public Location getLastLocationId() {
        return lastLocationId;
    }

    public void setLastLocationId(Location lastLocationId) {
        this.lastLocationId = lastLocationId;
    }

    public Wards getWardId() {
        return wardId;
    }

    public void setWardId(Wards wardId) {
        this.wardId = wardId;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.Location[ id=" + id + " ]";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Device> getDeviceSet() {
        return deviceSet;
    }

    public void setDeviceSet(Set<Device> deviceSet) {
        this.deviceSet = deviceSet;
    }
    
}