package com.reku.coolingloadestimation.batch;

import com.reku.coolingloadestimation.dto.EquipmentLoadDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EquipmentLoadFieldSetMapper implements FieldSetMapper<EquipmentLoadDTO> {
    @Override
    public EquipmentLoadDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        return new EquipmentLoadDTO(fieldSet.readLong("id"),
                                fieldSet.readString("name"),
                                fieldSet.readLong("equipmentLoad"));
    }
}
