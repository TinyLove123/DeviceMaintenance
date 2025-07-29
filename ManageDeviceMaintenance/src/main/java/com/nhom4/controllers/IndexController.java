/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.controllers;

import com.mysql.cj.conf.PropertyKey;
import static com.mysql.cj.conf.PropertyKey.logger;
import com.nhom4.pojo.AdministrativeUnits;
import com.nhom4.pojo.Category;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.RepairCost;
import com.nhom4.pojo.RepairType;
import com.nhom4.services.CategoryService;
import com.nhom4.services.DeviceService;
import com.nhom4.services.RepairTypeService;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Administrator
 */
@Controller
@ControllerAdvice
public class IndexController {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private CategoryService cateService;

    @Autowired
    private DeviceService DeviceService;

    @Autowired
    private RepairTypeService repairTypeService;

    @ModelAttribute
    public void commonResponse(Model model) {
        model.addAttribute("categories", this.cateService.getCates());
    }

    @RequestMapping("/admin")
    @Transactional
    public String Home(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("devices", this.DeviceService.getDevice(params));

        return "index";
    }

    @GetMapping("admin/devices/{id}")
    public String getDeviceDetail(@PathVariable("id") int id, Model model) {
        Device device = this.DeviceService.getDeviceById(id);
        List<RepairCost> repairCosts = this.DeviceService.getRepairTypeByDeviceId(id);
        List<RepairType> repairTypes = this.repairTypeService.getRepairType(); // ví dụ

        model.addAttribute("device", device);
        model.addAttribute("repairCosts", repairCosts);
        model.addAttribute("repairCost", new RepairCost());
        model.addAttribute("repairTypes", repairTypes);

        return "deviceDetail";
    }

    @PostMapping("admin/devices/{id}/add-repair")
    public String addRepairCost(@PathVariable("id") int deviceId, RepairCost r) {

        Device device = this.DeviceService.getDeviceById(deviceId);
        r.setId(null);
        r.setDeviceId(device);

        this.DeviceService.addOrUpdateRepairCost(r);

        return "redirect:/admin/devices/" + r.getDeviceId().getId();
    }

    @PostMapping("/admin/devices/{id}/update-repair")
    public String updateRepairCost(@ModelAttribute RepairCost repairCost,
            @PathVariable("id") int deviceId) {
        Device device = DeviceService.getDeviceById(deviceId);
        repairCost.setDeviceId(device);

        this.DeviceService.addOrUpdateRepairCost(repairCost);
        return "redirect:/admin/devices/" + deviceId;
    }

    @GetMapping("/admin/devices/{id}/delete")
    public String deleteDevice(@PathVariable("id") Integer id) {
        DeviceService.deleteDevice(id);
        return "redirect:/admin/devices-manager";
    }

    @GetMapping("/admin/devices/{deviceId}/repaircost/{repairCostId}/delete")
    public String deleteRepairCost(@PathVariable("deviceId") Integer deviceId,
            @PathVariable("repairCostId") Integer repairCostId) {
        DeviceService.deleteRepairCost(repairCostId);
        return "redirect:/admin/devices/" + deviceId;
    }
}
