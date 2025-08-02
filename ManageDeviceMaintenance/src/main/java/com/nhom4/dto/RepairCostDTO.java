/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.dto;

/**
 *
 * @author Administrator
 */
public class RepairCostDTO {

    private Integer id;
    private int price;
    private String repairTypeName; // Tên loại sửa chữa (từ repairTypeId)

    public RepairCostDTO() {
    }

    public RepairCostDTO(Integer id, int price, String repairTypeName) {
        this.id = id;
        this.price = price;
        this.repairTypeName = repairTypeName;
    }

    // Getters và setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRepairTypeName() {
        return repairTypeName;
    }

    public void setRepairTypeName(String repairTypeName) {
        this.repairTypeName = repairTypeName;
    }
}
