package com.nhom4.dto;

import com.nhom4.pojo.Device;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

public class DeviceDTO {

    private Integer id;
    private String nameDevice;
    private Date purchaseDate;
    private String manufacturer;
    private String statusDevice;
    private Integer frequency;
    private String image;
    private Double price;
    private Integer categoryId; // chỉ lấy id để tránh lồng object
    private MultipartFile file; // dùng để upload ảnh

    // Constructors
    public DeviceDTO() {
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

    }

    public DeviceDTO(Integer id, String nameDevice, Date purchaseDate, String manufacturer,
            String statusDevice, Integer frequency, String image,
            Double price, Integer categoryId, MultipartFile file) {
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
}
