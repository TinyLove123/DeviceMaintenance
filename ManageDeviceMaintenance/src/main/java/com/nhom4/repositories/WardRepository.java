/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories;

import com.nhom4.pojo.Wards;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface WardRepository {
    List<Wards> getWard(int provinceId);
}
