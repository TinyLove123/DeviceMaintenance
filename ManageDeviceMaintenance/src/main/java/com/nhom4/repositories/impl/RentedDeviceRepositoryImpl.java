/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.dto.RentedDeviceDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.Location;
import com.nhom4.pojo.RentedDevice;
import com.nhom4.pojo.RepairCost;
import com.nhom4.pojo.User;
import com.nhom4.repositories.LocationRepository;
import com.nhom4.repositories.RentedDeviceRepository;
import com.nhom4.services.LocationService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public class RentedDeviceRepositoryImpl implements RentedDeviceRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private LocationRepository locRepo;

    @Override
    public RentedDevice addRentedDevice(int deviceId, RentedDevice rentedDevice, Location location) {
        Session s = this.factory.getObject().getCurrentSession();

        Device device = s.get(Device.class, deviceId);
        if (device == null) {
            throw new IllegalArgumentException("Thiết bị không tồn tại!");
        }

        this.locRepo.addLocation(deviceId, location);

        device.setCurrentLocationId(location);
        s.merge(device);

        if (rentedDevice.getId() == null) {
            
            if (!"active".equalsIgnoreCase(device.getStatusDevice())) {
                throw new IllegalArgumentException("Thiết bị không ở trạng thái 'active'!");
            }

            rentedDevice.setDeviceId(device);
            rentedDevice.setIsRented(Boolean.TRUE);

            s.persist(rentedDevice);

            device.setStatusDevice("rented");
            s.merge(device); 

            return rentedDevice;
        } else {
            
            return s.merge(rentedDevice);
        }
    }

    @Override
    public List<RentedDeviceDTO> getMyRentedDevice(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<RentedDeviceDTO> cq = cb.createQuery(RentedDeviceDTO.class);
        Root<RentedDevice> root = cq.from(RentedDevice.class);
        Join<Object, Object> deviceJoin = root.join("deviceId");
        cq.select(cb.construct(
                RentedDeviceDTO.class,
                root.get("id"),
                root.get("startDate"),
                root.get("endDate"),
                root.get("isRented"),
                deviceJoin.get("nameDevice"),
                deviceJoin.get("manufacturer")
        )).where(cb.equal(root.get("customerId"), user));
        return s.createQuery(cq).getResultList();

    }

    @Override
    public RentedDevice getRentedDeviceById(User user, int rentedDeviceId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<RentedDevice> cq = cb.createQuery(RentedDevice.class);
        Root<RentedDevice> root = cq.from(RentedDevice.class);

        Fetch<RentedDevice, Device> deviceFetch = root.fetch("deviceId", JoinType.LEFT);
        Fetch<Device, RepairCost> repairCostFetch = deviceFetch.fetch("repairCostSet", JoinType.LEFT);
        repairCostFetch.fetch("repairTypeId", JoinType.LEFT);

        cq.select(root)
                .where(
                        cb.and(
                                cb.equal(root.get("id"), rentedDeviceId),
                                cb.equal(root.get("customerId"), user)
                        )
                );

        return s.createQuery(cq).uniqueResult();
    }

    @Override
    public boolean checkDeviceOwnership(int deviceId, int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root root = cq.from(RentedDevice.class);
        cq.select(cb.count(root))
                .where(
                        cb.and(
                                cb.equal(root.get("deviceId").get("id"), deviceId),
                                cb.equal(root.get("customerId").get("id"), userId),
                                cb.equal(root.get("isRented"), true)
                        ));
        Long count = s.createQuery(cq).uniqueResult();
        return count != null && count > 0;

    }

}
