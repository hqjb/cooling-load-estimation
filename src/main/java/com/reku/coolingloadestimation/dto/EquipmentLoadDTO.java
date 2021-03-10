package com.reku.coolingloadestimation.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class EquipmentLoadDTO {

    private Long id;
    private String name;
    private Long equipmentLoad;

    public EquipmentLoadDTO(Long id, String name, Long equipmentLoad) {
        this.id = id;
        this.name = name;
        this.equipmentLoad = equipmentLoad;
    }
}
