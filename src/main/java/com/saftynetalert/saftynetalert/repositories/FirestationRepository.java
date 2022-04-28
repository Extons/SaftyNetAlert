package com.saftynetalert.saftynetalert.repositories;

import com.saftynetalert.saftynetalert.entities.Address;
import com.saftynetalert.saftynetalert.entities.Firestation;
import com.saftynetalert.saftynetalert.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FirestationRepository extends JpaRepository<Firestation, Long> {

    Optional<Firestation> findByStation(Station station);
    Optional<Firestation> findByAddress(Address address);

    Optional<Firestation> findByStationName(String name);

    List<Firestation> findAllByAddress(Address address);
    List<Firestation> findAllByStation(Station station);
}
