package com.reku.coolingloadestimation;

import com.reku.coolingloadestimation.dto.EquipmentLoadDTO;
import com.reku.coolingloadestimation.service.IEquipmentLoadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CoolingLoadEstimationApplicationTests {

    @Autowired
    private IEquipmentLoadService equipmentLoadService;

    @Test
    void contextLoads() {
    }

    @Test
    void storeListOfEquipmentLoads() {
        List<EquipmentLoadDTO> equipmentLoadList;

        givenExcelFileWithListOfEquipment();
        whenApplicationStartsGetListOfEquipment();
        thenStoreListOfEquipmentInDatabase();
    }

    private void givenExcelFileWithListOfEquipment() {
    }

    private void whenApplicationStartsGetListOfEquipment() {

    }

    private void thenStoreListOfEquipmentInDatabase() {
        List<EquipmentLoadDTO> result = equipmentLoadService.findAll();
        assertNotNull(result);
    }

}
