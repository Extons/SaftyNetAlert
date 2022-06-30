package com.saftynetalert.saftynetalert.controllers;

import com.saftynetalert.saftynetalert.dto.RegistrationSuccessDto;
import com.saftynetalert.saftynetalert.dto.UserDto;
import com.saftynetalert.saftynetalert.entities.User;
import com.saftynetalert.saftynetalert.enums.Role;
import com.saftynetalert.saftynetalert.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/citizen/add")
    public RegistrationSuccessDto addCitizenUser(@RequestBody UserDto userDto){
        return userService.AddUser(userDto, Role.CITIZEN);
    }

    @PostMapping("/medical/add")
    public RegistrationSuccessDto addMedicalAssistUser(@RequestBody UserDto userDto){
        return userService.AddUser(userDto, Role.MEDICAL_ASSIST);
    }

    @PostMapping("/moderator/add")
    public RegistrationSuccessDto addModeratorUser(@RequestBody UserDto userDto){
        return userService.AddUser(userDto, Role.MODERATOR);
    }

    @GetMapping("/childAlerts")
    public List<User> sendChildAtAdress(@RequestParam String address) {
        return userService.sendChildAtAddress(address);
    }

    @GetMapping("/communityEmail")
    public List<String> findEmailByCity(@RequestParam String city) {
        return userService.findEmailByCity(city);
    }

    @GetMapping("/personInfo")
    public List<User> findUsersByFirstAndOrLastName(
            @RequestParam String firstname,
            @RequestParam String lastname
    ) {
        return userService.findUsersByFirstAndOrLastName(firstname, lastname);
    }

}
