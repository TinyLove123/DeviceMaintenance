/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.repositories.impl;

import com.nhom4.dto.RepairDetailDTO;
import com.nhom4.pojo.Device;
import com.nhom4.pojo.Incident;
import com.nhom4.pojo.Repair;
import com.nhom4.pojo.RepairCost;
import com.nhom4.pojo.RepairDetail;
import com.nhom4.pojo.User;
import com.nhom4.repositories.DeviceRepository;
import com.nhom4.repositories.RepairRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
public class RepairRepositoryImpl implements RepairRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private DeviceRepository deviceRepo;

    @Override
    public Repair getRepairByIncident(int incidentId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Repair> cq = cb.createQuery(Repair.class);
        Root<Repair> root = cq.from(Repair.class);

        root.fetch("repairDetailSet", JoinType.LEFT);

        cq.select(root)
                .where(cb.equal(root.get("incidentId").get("id"), incidentId));

        return s.createQuery(cq).uniqueResult();
    }
    
    

    @Override
    public void addOrUpdateRepairByIncidentId(Repair repair, Incident incident,
            User employee, Map<String, String> params
    ) {
        Session s = factory.getObject().getCurrentSession();

        Device device = s.get(Device.class, incident.getDeviceId().getId());

        if (repair.getId() == null) {
            repair.setIncidentId(incident);
            repair.setEmployeeId(employee);
            repair.setProgress("in_progress");

            device.setStatusDevice("in_repair");
            s.persist(repair);
        } else {
            Repair currentRepair = s.get(Repair.class, repair.getId());
            if (currentRepair != null) {
                currentRepair.setProgress(repair.getProgress());

                if ("completed".equalsIgnoreCase(repair.getProgress())) {
                    currentRepair.setEndDate(new Date());
                    device.setStatusDevice(incident.getReceptStatus());
                }

                s.merge(currentRepair);
            }
        }
    }

    @Override
    public void addRepairDetail(Repair repair, List<RepairDetailDTO> repairDetailList
    ) {
        Session s = factory.getObject().getCurrentSession();

        int deviceId = repair.getIncidentId().getDeviceId().getId();

        List<RepairCost> validRepairCosts = this.deviceRepo.getRepairCostByDeviceId(deviceId);
        Set<Integer> validRepairCostIds = validRepairCosts.stream()
                .map(RepairCost::getId)
                .collect(Collectors.toSet());

        for (RepairDetailDTO dto : repairDetailList) {
            if (!validRepairCostIds.contains(dto.getRepairCostId())) {
                throw new IllegalArgumentException("Chi phí sửa chữa không hợp lệ với thiết bị.");
            }

            RepairDetail detail = new RepairDetail();
            detail.setRepairId(repair);

            RepairCost repairCost = s.get(RepairCost.class, dto.getRepairCostId());
            detail.setRepairCostId(repairCost);
            detail.setDescriptionDetail(dto.getDescription());

            s.persist(detail);
        }
    }

    @Override
    public void deleteRepairDetail(int repairDetailId) {
         Session s = this.factory.getObject().getCurrentSession();
        RepairDetail r = this.getRepairDetailById(repairDetailId);
        if (r != null) {
            s.remove(r);
        }
    }

    @Override
    public RepairDetail getRepairDetailById(int repairDetailId) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(RepairDetail.class, repairDetailId);
    }

 
    

}
