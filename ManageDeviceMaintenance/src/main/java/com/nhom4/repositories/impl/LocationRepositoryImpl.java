/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.pojo.Device;
import com.nhom4.pojo.Location;
import com.nhom4.pojo.Provinces;
import com.nhom4.pojo.Wards;
import com.nhom4.repositories.LocationRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
public class LocationRepositoryImpl implements LocationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Provinces> getProvince() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder c = s.getCriteriaBuilder();
        CriteriaQuery q = c.createQuery(Provinces.class);
        Root root = q.from(Provinces.class);
        q.select(root);
        Query query = s.createQuery(q);

        return query.getResultList();

    }

    @Override
    public List<Wards> getWard(String provinceId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder c = s.getCriteriaBuilder();
        CriteriaQuery q = c.createQuery(Wards.class);
        Root root = q.from(Wards.class);
        q.select(root)
                .where(c.equal(root.get("provinceCode").get("code"), provinceId));

        return s.createQuery(q).getResultList();
    }

    @Override
    public List<Location> getLocationByDeviceId(int deviceId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder c = s.getCriteriaBuilder();
        CriteriaQuery cq = c.createQuery(Location.class);
        Root root = cq.from(Location.class);
        Predicate predicate = c.equal(root.get("deviceId").get("id"), deviceId);


        cq.select(root)
                .where(predicate)
                .orderBy(c.desc(root.get("lastUpdate")));

        return s.createQuery(cq).getResultList();
    }

    @Override
    public void addLocation(int deviceId, Location newLocation) {
        Session session = this.factory.getObject().getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Location> query = cb.createQuery(Location.class);
        Root<Location> root = query.from(Location.class);

        query.select(root)
                .where(
                        cb.equal(root.get("deviceId").get("id"), deviceId),
                        cb.isTrue(root.get("isCurrentLocation"))
                );

        List<Location> result = session.createQuery(query)
                .setMaxResults(1)
                .getResultList();

        if (!result.isEmpty()) {
            Location currentLoc = result.get(0);
            currentLoc.setIsCurrentLocation(false);
            newLocation.setLastLocationId(currentLoc);
            session.merge(currentLoc);
        }

        Device device = session.get(Device.class, deviceId);
        
        newLocation.setDeviceId(device);
        newLocation.setIsCurrentLocation(true);
        newLocation.setLastUpdate(new Date());

        newLocation.setId(null); 
        session.persist(newLocation);
        device.setCurrentLocationId(newLocation);
        session.persist(device);
    }

    @Override
    public Location getLocationById(int locationId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Location> cq = cb.createQuery(Location.class);
        Root<Location> root = cq.from(Location.class);

        cq.select(root).where(cb.equal(root.get("id"), locationId));

        return s.createQuery(cq).getSingleResult();
    }

}
