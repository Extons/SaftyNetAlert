package com.saftynetalert.saftynetalert.services;

import com.saftynetalert.saftynetalert.dto.AddressDto;
import com.saftynetalert.saftynetalert.entities.Address;
import com.saftynetalert.saftynetalert.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address AddAddress(AddressDto addressDto){
        boolean isAddressExist = addressRepository.findByAddressId(addressDto.toAddressId()).isPresent();
        if(!isAddressExist){
            Address address = new Address();
            address.setAddressId(addressDto.toAddressId());

            addressRepository.save(address);

            return address;
        }

        return null;
    }
}
