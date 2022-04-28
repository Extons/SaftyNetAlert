package com.saftynetalert.saftynetalert.controllers;

import com.saftynetalert.saftynetalert.dto.FirestationDto;
import com.saftynetalert.saftynetalert.entities.Firestation;
import com.saftynetalert.saftynetalert.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/firestation")
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @PostMapping("/add")
    public Firestation add(@RequestBody FirestationDto firestationDto){
        return firestationService.add(firestationDto);
    }
}
