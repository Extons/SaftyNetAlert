package com.saftynetalert.saftynetalert.controllers;

import com.saftynetalert.saftynetalert.dto.UserDto;
import com.saftynetalert.saftynetalert.entities.User;
import com.saftynetalert.saftynetalert.enums.Role;
import com.saftynetalert.saftynetalert.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/citizen/add")
    public String addCitizenUser(@RequestBody UserDto userDto){
        return userService.AddUser(userDto, Role.CITIZEN);
    }

    @PostMapping("/medical/add")
    public String addMedicalAssistUser(@RequestBody UserDto userDto){
        return userService.AddUser(userDto, Role.MEDICAL_ASSIST);
    }

    @PostMapping("/moderator/add")
    public String addModeratorUser(@RequestBody UserDto userDto){
        return userService.AddUser(userDto, Role.MODERATOR);
    }

}
