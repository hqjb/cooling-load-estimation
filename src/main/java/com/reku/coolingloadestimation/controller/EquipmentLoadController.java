package com.reku.coolingloadestimation.controller;

import com.reku.coolingloadestimation.dto.EquipmentLoadDTO;
import com.reku.coolingloadestimation.service.EquipmentLoadService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
public class EquipmentLoadController {

    @Autowired
    EquipmentLoadService equipmentLoadService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("eqLoadProcessJob")
    Job processJob;

    @GetMapping("/invokejob/{name}/{eqload}")
    public String submitEquipmentLoad(@PathVariable String name, @PathVariable Long eqload) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder().addString("name", name).addLong("eqload", eqload)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(processJob, jobParameters);

        return String.format("Batch job has been invoked and equipment load inserted : %s of load %s Watts.", name, eqload);
    }

    @GetMapping("/")
    public List<EquipmentLoadDTO> getEquipmentLoads() {
        return equipmentLoadService.findAll();
    }
}
