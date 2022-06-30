package com.saftynetalert.saftynetalert.controllers;

import com.saftynetalert.saftynetalert.dto.FirestationDto;
import com.saftynetalert.saftynetalert.dto.UserDto;
import com.saftynetalert.saftynetalert.entities.Firestation;
import com.saftynetalert.saftynetalert.entities.User;
import com.saftynetalert.saftynetalert.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("")
    public List<Firestation> retrieveAll() {
        return firestationService.retrieveAll();
    }

    @GetMapping("/params")
    public List<User> findPersonsByFirestationNumber(
            @RequestParam() int stationNumber) {
        return firestationService.findPersonsByFirestationNumber(stationNumber);
    }

    @GetMapping("/phoneAlerts")
    public List<String> findUsersPhoneByFirestationNumber(@RequestParam Long firestationNumber) {
        return firestationService.findUsersPhoneByFirestationNumber(firestationNumber);
    }
}
