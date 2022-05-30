package com.saftynetalert.saftynetalert.services;

import com.saftynetalert.saftynetalert.dto.UserDto;
import com.saftynetalert.saftynetalert.entities.AddressId;
import com.saftynetalert.saftynetalert.entities.MedicalRecord;
import com.saftynetalert.saftynetalert.entities.User;
import com.saftynetalert.saftynetalert.entities.ERole;
import com.saftynetalert.saftynetalert.repositories.AddressRepository;
import com.saftynetalert.saftynetalert.repositories.MedicalRecordRepository;
import com.saftynetalert.saftynetalert.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final AddressRepository addressRepository;

    public User AddUser(UserDto userDto, ERole ERole){
        AddressId addressId = userDto.getAddress().toAddressId();

        return addressRepository.findByAddressId(addressId).map(address -> {

            if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "email_already_exist");
            }

            User user = new User();
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setDescription(userDto.getMedicalRecord().getDescription());
            medicalRecord.setMedications(Arrays.stream(userDto.getMedicalRecord().getMedications()).toList());
            medicalRecord.setAllergies(Arrays.stream(userDto.getMedicalRecord().getAllergies()).toList());

            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setBirthdate(userDto.getBirthdate());
            user.setEmail(userDto.getEmail());
            user.setPhone(userDto.getPhone());
            user.setPassword(userDto.getPassword()); // TODO : Should be encrypted !
            user.setMedicalRecord(medicalRecord);
            user.setAddress(address);

            medicalRecordRepository.save(medicalRecord);
            userRepository.save(user);

            return user;
        }).orElseThrow(
            () -> {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "address_not_found");
            }
        );
    }
}
