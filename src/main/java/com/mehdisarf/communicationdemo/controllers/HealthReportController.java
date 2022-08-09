package com.mehdisarf.communicationdemo.controllers;

import com.mehdisarf.communicationdemo.entities.HealthReport;
import com.mehdisarf.communicationdemo.services.HealthReportData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthReportController {

    private final HealthReportData healthReportData;

    @Autowired
    public HealthReportController(HealthReportData healthReportData) {
        this.healthReportData = healthReportData;
    }

    @GetMapping("/healthReport")
    public HealthReport getHealthReport(){
        return healthReportData.generateHealthReport();
    }
}
