package com.saftynetalert.saftynetalert.services;

import com.saftynetalert.saftynetalert.dto.AddressDto;
import com.saftynetalert.saftynetalert.entities.Address;
import com.saftynetalert.saftynetalert.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "address_already_exist");
        }
    }

    public Address GetAddress(AddressDto addressDto){
        var address = addressRepository.findByAddressId(addressDto.toAddressId());
        boolean isAddressExist = address.isPresent();
        if(isAddressExist){
            return address.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "address_not_exist");
        }
    }
}
