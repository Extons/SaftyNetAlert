package com.saftynetalert.saftynetalert.services;

import com.saftynetalert.saftynetalert.dto.FirestationDto;
import com.saftynetalert.saftynetalert.entities.Address;
import com.saftynetalert.saftynetalert.entities.Firestation;
import com.saftynetalert.saftynetalert.entities.Station;
import com.saftynetalert.saftynetalert.repositories.AddressRepository;
import com.saftynetalert.saftynetalert.repositories.FirestationRepository;
import com.saftynetalert.saftynetalert.repositories.StationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final StationRepository stationRepository;
    private final AddressRepository addressRepository;

    public Firestation add(FirestationDto firestationDto) {

        Station station = stationRepository.findByName(firestationDto.getStation().getName()).get();
        Address address = addressRepository.findByAddressId(firestationDto.getAddress().toAddressId()).get();

        boolean isFirestationExist =
                   firestationRepository.findByStation(station).isPresent()
                && firestationRepository.findByAddress(address).isPresent();

        if (!isFirestationExist)
        {
            Firestation firestation = new Firestation();
            firestation.setStation(station);
            firestation.setAddress(address);

            firestationRepository.save(firestation);
            return firestation;
        } else {
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
}
