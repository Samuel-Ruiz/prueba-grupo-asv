package com.grupoasv.prueba.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerDemo {

    private final ServiceDemo service;

    @Autowired
    public SchedulerDemo(ServiceDemo service){
        this.service = service;
    }

    @Scheduled(fixedDelay = 5000)
    public void executePendingTransfers(){
        this.service.executePendingTransfers();
    }

    @Scheduled(fixedDelay = 5000)
    public void executeAmbulanceEndTransfer(){
        this.service.executeAmbulanceEndTransfer();
    }
}
