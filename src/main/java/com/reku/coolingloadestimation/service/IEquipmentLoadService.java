package com.reku.coolingloadestimation.service;

import com.reku.coolingloadestimation.dto.EquipmentLoadDTO;
import com.reku.coolingloadestimation.entity.EquipmentLoad;

import java.util.List;

/**
 * Service and rest of app should input and output DTO objects but use mapper to convert to entity for repo
 */
public interface IEquipmentLoadService {
    List<EquipmentLoadDTO> saveAll(List<EquipmentLoadDTO> equipmentLoadListDTO);
    EquipmentLoadDTO save(EquipmentLoadDTO equipmentLoadDTO);
    List<EquipmentLoadDTO> findAll();
}
