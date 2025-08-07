package com.nhom4.controllers;

import com.nhom4.pojo.Category;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.Location;
import com.nhom4.pojo.RepairCost;
import com.nhom4.pojo.RepairType;
import com.nhom4.pojo.Wards;
import com.nhom4.repositories.LocationRepository;
import com.nhom4.services.CategoryService;
import com.nhom4.services.DeviceService;
import com.nhom4.services.LocationService;
import com.nhom4.services.RepairTypeService;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/devices-manager")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RepairTypeService repairTypeService;

    @Autowired
    private LocationService locationService;

    @GetMapping
    @Transactional
    public String listDevices(Model model,
            @RequestParam Map<String, String> params) {
        model.addAttribute("devices", deviceService.getDevice(params));

        model.addAttribute("categories", categoryService.getCates());

        return "deviceManage";
    }

    @GetMapping("/wards")
    @ResponseBody
    public List<Wards> getWards(@RequestParam("provinceId") String provinceId) {
        return this.locationService.getWard(provinceId);
    }

    @GetMapping("/add")
    public String addDeviceForm(Model model) {
        model.addAttribute("device", new Device());

        model.addAttribute("categories", this.categoryService.getCates());
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

    @GetMapping("/devices/{id}/delete")
    public String deleteDevice(@PathVariable("id") Integer id) {
        deviceService.deleteDevice(id);
        return "redirect:/admin/devices-manager";
    }

    @GetMapping("/devices/{id}")
    public String getDeviceDetail(@PathVariable("id") int id, Model model) {
        Device device = this.deviceService.getDeviceById(id);
        List<RepairType> repairTypes = this.repairTypeService.getRepairType();

        model.addAttribute("device", device);
        model.addAttribute("repairCosts", device.getRepairCostSet());
        model.addAttribute("currentLocation", device.getLocationSet());
        model.addAttribute("repairCost", new RepairCost());
        model.addAttribute("location", new Location());
        model.addAttribute("repairTypes", repairTypes);
        model.addAttribute("provinces", this.locationService.getProvince());

        return "deviceDetail";
    }

    @GetMapping("/devices/{id}/update")
    public String updateDevicePage(@PathVariable("id") int id, Model model) {
        Device device = this.deviceService.getDeviceById(id);
        List<RepairType> repairTypes = this.repairTypeService.getRepairType();
        model.addAttribute("device", device);
        model.addAttribute("repairCosts", device.getRepairCostSet());
        model.addAttribute("currentLocation", device.getLocationSet());
        model.addAttribute("repairCost", new RepairCost());
        model.addAttribute("location", new Location());
        model.addAttribute("repairTypes", repairTypes);
        model.addAttribute("provinces", this.locationService.getProvince());

        return "updateDevice";
    }

    @PostMapping("/devices/{id}/update-device")
    public String updateDevice(@PathVariable("id") int id, @ModelAttribute("device") Device device, Model model) {
        Device d = this.deviceService.getDeviceById(id);
        d.setNameDevice(device.getNameDevice());
        d.setManufacturer(device.getManufacturer());
        d.setFrequency(device.getFrequency());
        d.setFile(device.getFile());

        this.deviceService.addOrUpdateDevice(d);

        return "redirect:/admin/devices-manager/devices/" + d.getId() + "/update";
    }

    @PostMapping("/devices/{id}/add-repair")
    public String addRepairCost(@PathVariable("id") int deviceId, RepairCost r) {

        Device device = this.deviceService.getDeviceById(deviceId);
        r.setId(null);
        r.setDeviceId(device);

        this.deviceService.addOrUpdateRepairCost(r);

        return "redirect:/admin/devices-manager/devices/" + r.getDeviceId().getId();
    }

    @PostMapping("/devices/{id}/update-repair")
    public String updateRepairCost(@ModelAttribute RepairCost repairCost,
            @PathVariable("id") int deviceId) {
        Device device = deviceService.getDeviceById(deviceId);
        repairCost.setDeviceId(device);

        this.deviceService.addOrUpdateRepairCost(repairCost);
        return "redirect:/admin/devices-manager/devices/" + deviceId;
    }

    @GetMapping("/devices/{deviceId}/repaircost/{repairCostId}/delete")
    public String deleteRepairCost(@PathVariable("deviceId") Integer deviceId,
            @PathVariable("repairCostId") Integer repairCostId) {
        deviceService.deleteRepairCost(repairCostId);
        return "redirect:/admin/devices-manager/devices/" + deviceId;
    }

    @PostMapping("/devices/{id}/update-location")
    public String updateLocation(Model model, @PathVariable("id") int deviceId, Location loc) {

        this.locationService.addLocation(deviceId, loc);
        return "redirect:/admin/devices-manager/devices/" + deviceId;

    }

    @GetMapping("/device/{id}/location-history")
    public String getHistoryLocation(Model model, @PathVariable("id") Integer deviceId,
            Principal principal) {
        List<Location> historyLocation = this.locationService.getLocationByDeviceId(deviceId);
        model.addAttribute("LocationHistory", historyLocation);
        return "deviceHistoryLocation";
    }

}
