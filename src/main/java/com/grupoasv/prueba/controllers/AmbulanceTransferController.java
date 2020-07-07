package com.grupoasv.prueba.controllers;

import com.grupoasv.prueba.exceptions.ExceptionService;
import com.grupoasv.prueba.model.ambulance.AmbulanceDto;
import com.grupoasv.prueba.model.patient.JourneyDto;
import com.grupoasv.prueba.services.AmbulanceTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AmbulanceTransferController{

    private final AmbulanceTransferService service;

    @Autowired
    public AmbulanceTransferController(AmbulanceTransferService service){
        this.service = service;
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/ambulances", consumes = "application/json")
    public ResponseEntity<String> updateAmbulancesOnService(
            @RequestBody
            final AmbulanceDto[] ambulances){
        try{
            this.service.updateAmbulances(ambulances);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(ExceptionService exception){
            return new ResponseEntity<>(exception.getReason(), exception.getStatus());
        }
    }

    @GetMapping(value = "/ambulances", produces = "application/json")
    public AmbulanceDto[] getAmbulances(){
        return this.service.getAmbulances();
    }

    @PostMapping(value = "/journey", consumes = "application/json")
    public ResponseEntity<String> journey(
            @RequestBody
            final JourneyDto journeyDto){
        try{
            this.service.createJourney(journeyDto);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(ExceptionService exception){
            return new ResponseEntity<>(exception.getReason(), exception.getStatus());
        }
    }

    @GetMapping(value = "/journey", produces = "application/json")
    public JourneyDto[] getJourney(){
        return this.service.getJourney();
    }

    @PostMapping(value = "/transfer", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> transfer(final Integer ID){
        try{
            this.service.transferPatientGroup(ID);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(ExceptionService exception){
            return new ResponseEntity<>(exception.getReason(), exception.getStatus());
        }
    }

    @PostMapping(value = "/locate", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> locate(final Integer ID){
        try{
            Integer ambulanceId = this.service.locate(ID);
            return new ResponseEntity<>(ambulanceId.toString(), HttpStatus.OK);
        }catch(ExceptionService exception){
            return new ResponseEntity<>(exception.getReason(), exception.getStatus());
        }
    }
}
