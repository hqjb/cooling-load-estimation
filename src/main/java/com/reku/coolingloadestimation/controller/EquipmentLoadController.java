package com.reku.coolingloadestimation.controller;

import com.reku.coolingloadestimation.dto.EquipmentLoadDTO;
import com.reku.coolingloadestimation.service.EquipmentLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
public class EquipmentLoadController {

    @Autowired
    EquipmentLoadService equipmentLoadService;

    @GetMapping("/")
    public List<EquipmentLoadDTO> getEquipmentLoads() {
        return equipmentLoadService.findAll();
    }
}
