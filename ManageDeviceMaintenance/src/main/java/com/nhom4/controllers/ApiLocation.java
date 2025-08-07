package com.nhom4.controllers;

import com.nhom4.pojo.Provinces;
import com.nhom4.pojo.Wards;
import com.nhom4.services.LocationService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * API trả về thông tin địa lý: Tỉnh và Phường
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiLocation {

    @Autowired
    private LocationService locationService;

    @GetMapping("/provinces")
    public List<Provinces> getProvinces() {
        return locationService.getProvince();
    }

    @GetMapping("/provinces/{provinceId}/wards")
    public List<Wards> getWards(@PathVariable("provinceId") String provinceId) {
        return locationService.getWard(provinceId);
    }
}
