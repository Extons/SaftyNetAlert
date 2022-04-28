package com.saftynetalert.saftynetalert.entities;

import jdk.jfr.SettingDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class AddressId implements Serializable  {

    private String address;
    private Long zip;
    private String city;
    private String state;

}
