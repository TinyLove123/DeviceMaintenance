package com.nhom4.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.Location;

public class DeviceDTO {

    private Integer id;
    private String nameDevice;
    private Date purchaseDate;
    private String manufacturer;
    private String statusDevice;
    private Integer frequency;
    private String image;
    private Double price;
    private Integer categoryId; 
    private MultipartFile file;
    private Location location;

    // Constructors
    public DeviceDTO() {
    }
       

    public DeviceDTO(Integer id, String nameDevice, String manufacturer, 
            String statusDevice,Date purchaseDate, Integer frequency,
            String image, Double price,Location currentLocationId) {
        this.id = id;
        this.nameDevice = nameDevice;
        this.manufacturer = manufacturer;
        this.statusDevice = statusDevice;
        this.location = currentLocationId;
        this.purchaseDate = purchaseDate;
        this.frequency = frequency;
        this.image = image;
        this.price = price;
    }

    public DeviceDTO(Device device) {
        this.id = device.getId();
        this.nameDevice = device.getNameDevice();
        this.purchaseDate = device.getPurchaseDate();
        this.manufacturer = device.getManufacturer();
        this.statusDevice = device.getStatusDevice();
        this.frequency = device.getFrequency();
        this.image = device.getImage();
        this.price = device.getPrice();
        this.categoryId = device.getCategoryId() != null ? device.getCategoryId().getId() : null;
        this.file = null;
        this.location = device.getCurrentLocationId();

    }

    public DeviceDTO(Integer id, String nameDevice, Date purchaseDate, String manufacturer,
            String statusDevice, Integer frequency, String image,
            Double price, Integer categoryId, MultipartFile file, Location location) {
        this.id = id;
        this.nameDevice = nameDevice;
        this.purchaseDate = purchaseDate;
        this.manufacturer = manufacturer;
        this.statusDevice = statusDevice;
        this.frequency = frequency;
        this.image = image;
        this.price = price;
        this.categoryId = categoryId;
        this.file = file;
        this.location = location;
    }

    

    // Getters and setters
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
