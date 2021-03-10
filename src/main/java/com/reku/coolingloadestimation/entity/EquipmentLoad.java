package com.reku.coolingloadestimation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "equipment_load")
public class EquipmentLoad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long equipmentLoad;

    public EquipmentLoad(Long id, String name, Long equipmentLoad) {
        this.id = id;
        this.name = name;
        this.equipmentLoad = equipmentLoad;
    }
}
