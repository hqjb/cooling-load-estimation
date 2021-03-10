package com.reku.coolingloadestimation.util;

import com.reku.coolingloadestimation.dto.EquipmentLoadDTO;
import com.reku.coolingloadestimation.entity.EquipmentLoad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipmentLoadMapper {

    @Mappings({
            @Mapping(target="id", source = "equipmentLoad.id"),
            @Mapping(target="name", source = "equipmentLoad.name"),
            @Mapping(target="equipmentLoad", source = "equipmentLoad.equipmentLoad")})
    EquipmentLoadDTO toEquipmentLoadDto(EquipmentLoad equipmentLoad);

    @Mappings({
            @Mapping(target="id", source = "equipmentLoadDTO.id"),
            @Mapping(target="name", source = "equipmentLoadDTO.name"),
            @Mapping(target="equipmentLoad", source = "equipmentLoadDTO.equipmentLoad")})
    EquipmentLoad toEquipmentLoad(EquipmentLoadDTO equipmentLoadDTO);

    List<EquipmentLoadDTO> toEquipmentLoadDtos(List<EquipmentLoad> equipmentLoads);

    List<EquipmentLoad> toEquipmentLoads(List<EquipmentLoadDTO> equipmentLoads);
}
