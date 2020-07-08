package com.grupoasv.prueba.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerDemo{

    @Value("${demo.enabled:false}")
    private boolean isEnabled;

    private final ServiceDemo service;

    @Autowired
    public SchedulerDemo(ServiceDemo service){
        this.service = service;
    }

    @Scheduled(fixedDelay = 5000)
    public void executePendingTransfers(){
        if(isEnabled){
            this.service.executePendingTransfers();
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void executeAmbulanceEndTransfer(){
        if(isEnabled){
            this.service.executeAmbulanceEndTransfer();
        }
    }
}
