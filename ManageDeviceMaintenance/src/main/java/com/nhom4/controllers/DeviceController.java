package com.nhom4.controllers;

import com.nhom4.pojo.Device;
import com.nhom4.services.CategoryService;
import com.nhom4.services.DeviceService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/devices-manager")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Transactional
    public String listDevices(Model model,
            @RequestParam Map<String, String> params) {
        model.addAttribute("devices", deviceService.getDevice(params));
        model.addAttribute("categories", categoryService.getCates());
        
        return "deviceManage";
    }

    @GetMapping("/{id}")
    public String deviceDetail(@PathVariable Integer id, Model model) {
        Device device = deviceService.getDeviceById(id);
        if (device == null) {
            return "redirect:/devices-manager"; // Nếu không tìm thấy, quay về trang danh sách
        }
        model.addAttribute("device", device);
        return "deviceDetail"; // Trả về trang chi tiết
    }

    @GetMapping("/add")
    public String addDeviceForm(Model model) {
        model.addAttribute("device", new Device());
        model.addAttribute("categories", categoryService.getCates());
        return "addDevice";
    }

    @PostMapping("/add-device")
    public String add(@ModelAttribute("device") Device d) {
        System.out.println(d.getCategoryId());
        this.deviceService.addOrUpdateDevice(d);
        return "redirect:/devices-manager";
    }


}
