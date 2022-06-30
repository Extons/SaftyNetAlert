package com.saftynetalert.saftynetalert.services;

import com.saftynetalert.saftynetalert.dto.FirestationDto;
import com.saftynetalert.saftynetalert.dto.UserDto;
import com.saftynetalert.saftynetalert.entities.Address;
import com.saftynetalert.saftynetalert.entities.Firestation;
import com.saftynetalert.saftynetalert.entities.Station;
import com.saftynetalert.saftynetalert.entities.User;
import com.saftynetalert.saftynetalert.repositories.AddressRepository;
import com.saftynetalert.saftynetalert.repositories.FirestationRepository;
import com.saftynetalert.saftynetalert.repositories.StationRepository;
import com.saftynetalert.saftynetalert.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final StationRepository stationRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public Firestation add(FirestationDto firestationDto) {
        Station station = stationRepository.findByName(firestationDto.getStation().getName()).get();
        Address address = addressRepository.findByAddressId(firestationDto.getAddress().toAddressId()).get();

        List<Firestation> allFirestations =
                firestationRepository.findAllByAddress_AddressIdAndStation_Name(
                        firestationDto.getAddress().toAddressId(),
                        firestationDto.getStation().getName()
                );

        boolean isFirestationExist =  allFirestations.size() > 0;


        if (!isFirestationExist)
        {
            Firestation firestation = new Firestation();
            firestation.setStation(station);
            firestation.setAddress(address);

            firestationRepository.save(firestation);
            return firestation;
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "firestation_already_exist");
        }
    }

    public boolean remove(Firestation firestation) {
        if (firestationRepository.findById(firestation.getId()).isPresent()) {
            firestationRepository.delete(firestation);
            return true;
        }

        return false;
    }

    public List<User> findPersonsByFirestationNumber(int stationNumber) {
        List<Firestation> firestationList = firestationRepository.findAll();
        List<Address> addressList = new ArrayList<Address>();
        List<User> userList = userRepository.findAll();
        List<User> response = new ArrayList<User>();
        int mineur = 0;
        int majeur = 0;

        for (var firestation:firestationList) {
            if (firestation.getStation().getId() == stationNumber) {
                addressList.add(firestation.getAddress());
            }
        }
        for (var user:userList) {
            if (addressList.contains(user.getAddress())) {
                response.add(user);
            }
        }
        for (var respUser: response) {
            if (((LocalDateTime.now().getYear()) - (respUser.getBirthdate().toLocalDate().getYear()) < 18)) {
                mineur++;
            } else {
                majeur++;
            }
        }

//        if (!stationNumber){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Station with id" + stationNumber + "does not exist");
//        }
        return response;
    }

    public List<String> findUsersPhoneByFirestationNumber(Long firestationNumber) {
        Optional<Firestation> firestation = firestationRepository.findById(firestationNumber);
        List<User> userList = userRepository.findAll();
        List<String> phoneList = new ArrayList<String>();
        if (firestation.isPresent()) {
            for (var user:userList) {
                if ((user.getAddress().getAddressId().getAddress()).equals(firestation.get().getAddress().getAddressId().getAddress())) {
                    phoneList.add(user.getPhone());
                }
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "firestation with id" + firestationNumber + "does not exist");
        }
        return phoneList;
    }

    public List<Firestation> findAll() {
        return firestationRepository.findAll();
    }
}
