package com.nhom4.controllers;

import com.nhom4.pojo.Category;
import com.nhom4.pojo.Device;
import com.nhom4.services.CategoryService;
import com.nhom4.services.DeviceService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/devices-manager")
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
            return "redirect:/admin/devices-manager";
        }
        model.addAttribute("device", device);
        return "deviceDetail";
    }

    @GetMapping("/add")
    public String addDeviceForm(Model model) {
        model.addAttribute("device", new Device());
        model.addAttribute("categories", categoryService.getCates());
        return "addDevice";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping("/add-device")
    public String add(@ModelAttribute("device") Device d) {
        this.deviceService.addOrUpdateDevice(d);
        return "redirect:/admin/devices-manager";
    }

}
