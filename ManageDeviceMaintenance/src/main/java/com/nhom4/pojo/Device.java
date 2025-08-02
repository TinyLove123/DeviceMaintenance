/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "device")
@NamedQueries({
    @NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d"),
    @NamedQuery(name = "Device.findById", query = "SELECT d FROM Device d WHERE d.id = :id"),
    @NamedQuery(name = "Device.findByNameDevice", query = "SELECT d FROM Device d WHERE d.nameDevice = :nameDevice"),
    @NamedQuery(name = "Device.findByPurchaseDate", query = "SELECT d FROM Device d WHERE d.purchaseDate = :purchaseDate"),
    @NamedQuery(name = "Device.findByManufacturer", query = "SELECT d FROM Device d WHERE d.manufacturer = :manufacturer"),
    @NamedQuery(name = "Device.findByStatusDevice", query = "SELECT d FROM Device d WHERE d.statusDevice = :statusDevice"),
    @NamedQuery(name = "Device.findByFrequency", query = "SELECT d FROM Device d WHERE d.frequency = :frequency"),
    @NamedQuery(name = "Device.findByImage", query = "SELECT d FROM Device d WHERE d.image = :image"),
    @NamedQuery(name = "Device.findByPrice", query = "SELECT d FROM Device d WHERE d.price = :price")})
public class Device implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name_device")
    private String nameDevice;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "manufacturer")
    private String manufacturer;
    @Size(max = 20)
    @Column(name = "status_device")
    private String statusDevice;
    @Size(max = 200)
    @Column(name = "image")
    private String image;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "purchase_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;
    @Column(name = "frequency")
    private Integer frequency;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceId")
    private Set<RepairCost> repairCostSet;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceId")
    private Set<MaintenanceSchedule> maintenanceScheduleSet;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceId")
    private Set<RentedDevice> rentedDeviceSet;
    @JsonIgnore
    @OneToMany(mappedBy = "deviceId")
    private Set<Location> locationSet;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Category categoryId;
    @JoinColumn(name = "current_location_id", referencedColumnName = "id")
    @ManyToOne
    private Location currentLocationId;
    @JsonIgnore
    @OneToMany(mappedBy = "deviceId")
    private Set<Incident> incidentSet;

    @Transient
    private MultipartFile file;

    public Device() {
    }

    public Device(Integer id) {
        this.id = id;
    }

    public Device(Integer id, String nameDevice, String manufacturer) {
        this.id = id;
        this.nameDevice = nameDevice;
        this.manufacturer = manufacturer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }


    public String getStatusDevice() {
        return statusDevice;
    }

    public void setStatusDevice(String statusDevice) {
        this.statusDevice = statusDevice;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<RepairCost> getRepairCostSet() {
        return repairCostSet;
    }

    public void setRepairCostSet(Set<RepairCost> repairCostSet) {
        this.repairCostSet = repairCostSet;
    }

    public Set<MaintenanceSchedule> getMaintenanceScheduleSet() {
        return maintenanceScheduleSet;
    }

    public void setMaintenanceScheduleSet(Set<MaintenanceSchedule> maintenanceScheduleSet) {
        this.maintenanceScheduleSet = maintenanceScheduleSet;
    }

    public Set<RentedDevice> getRentedDeviceSet() {
        return rentedDeviceSet;
    }

    public void setRentedDeviceSet(Set<RentedDevice> rentedDeviceSet) {
        this.rentedDeviceSet = rentedDeviceSet;
    }

    public Set<Location> getLocationSet() {
        return locationSet;
    }

    public void setLocationSet(Set<Location> locationSet) {
        this.locationSet = locationSet;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Location getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(Location currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public Set<Incident> getIncidentSet() {
        return incidentSet;
    }

    public void setIncidentSet(Set<Incident> incidentSet) {
        this.incidentSet = incidentSet;
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
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.Device[ id=" + id + " ]";
    }

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
