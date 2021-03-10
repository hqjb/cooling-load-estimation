package com.reku.coolingloadestimation.service;

import com.reku.coolingloadestimation.dto.EquipmentLoadDTO;
import com.reku.coolingloadestimation.entity.EquipmentLoad;
import com.reku.coolingloadestimation.repository.IEquipmentLoadRepository;
import com.reku.coolingloadestimation.util.EquipmentLoadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentLoadService implements IEquipmentLoadService{

    @Autowired
    EquipmentLoadMapper equipmentLoadMapper;

    @Autowired
    IEquipmentLoadRepository equipmentLoadRepository;

    @Override
    public List<EquipmentLoadDTO> saveAll(List<EquipmentLoadDTO> equipmentLoadListDTO) {

        List<EquipmentLoad> result = equipmentLoadRepository.saveAll(equipmentLoadMapper.toEquipmentLoads(equipmentLoadListDTO));

        List<EquipmentLoadDTO> resultDTO = equipmentLoadMapper.toEquipmentLoadDtos(result);

        return resultDTO;
    }

    @Override
    public EquipmentLoadDTO save(EquipmentLoadDTO equipmentLoadDTO) {

        EquipmentLoad result = equipmentLoadRepository.save(equipmentLoadMapper.toEquipmentLoad(equipmentLoadDTO));

        EquipmentLoadDTO resultDTO = equipmentLoadMapper.toEquipmentLoadDto(result);

        return resultDTO;
    }

    @Override
    public List<EquipmentLoadDTO> findAll() {

        List<EquipmentLoad> result = equipmentLoadRepository.findAll();

        List<EquipmentLoadDTO> resultDTO = equipmentLoadMapper.toEquipmentLoadDtos(result);

        return resultDTO;
    }
}
