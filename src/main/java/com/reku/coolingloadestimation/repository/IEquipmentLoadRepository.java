package com.reku.coolingloadestimation.repository;

import com.reku.coolingloadestimation.entity.EquipmentLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository should interact only with entity objects
 */
@Repository
public interface IEquipmentLoadRepository extends JpaRepository<EquipmentLoad, Long> {
//    List<EquipmentLoad> saveAll(List<EquipmentLoad> equipmentLoadList);
    EquipmentLoad save(EquipmentLoad equipmentLoad);
    List<EquipmentLoad> findAll();
}
