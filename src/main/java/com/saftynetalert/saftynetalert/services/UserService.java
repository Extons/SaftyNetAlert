package com.saftynetalert.saftynetalert.services;

import com.saftynetalert.saftynetalert.dto.UserDto;
import com.saftynetalert.saftynetalert.entities.AddressId;
import com.saftynetalert.saftynetalert.entities.MedicalRecord;
import com.saftynetalert.saftynetalert.entities.User;
import com.saftynetalert.saftynetalert.enums.Role;
import com.saftynetalert.saftynetalert.registration.token.ConfirmationToken;
import com.saftynetalert.saftynetalert.registration.token.ConfirmationTokenService;
import com.saftynetalert.saftynetalert.repositories.AddressRepository;
import com.saftynetalert.saftynetalert.repositories.MedicalRecordRepository;
import com.saftynetalert.saftynetalert.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService
{
    private final String NOT_FOUND_EMAIL_EXCEPTION = "User with %s email not found";
    private final String EXIST_EMAIL_EXCEPTION = "%s email already exist";
    private final String EXIST_PHONE_EXCEPTION = "%s phone already exist";

    private final UserRepository userRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final AddressRepository addressRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException(String.format(NOT_FOUND_EMAIL_EXCEPTION, email))
        );
    }

    public String AddUser(UserDto userDto, Role role){
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

            String encryptedPwd = bCryptPasswordEncoder.encode(userDto.getPassword());

            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setBirthdate(userDto.getBirthdate());
            user.setEmail(userDto.getEmail());
            user.setPhone(userDto.getPhone());
            user.setPassword(encryptedPwd);
            user.setMedicalRecord(medicalRecord);
            user.setAddress(address);
            user.setRole(role);

            medicalRecordRepository.save(medicalRecord);
            userRepository.save(user);

            String token = UUID.randomUUID().toString();

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    user
            );

            confirmationTokenService.saveConfirmationToken(confirmationToken);

            // TODO : SEND EMAIL

            return token;
        }).orElseThrow(
            () -> {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "address_not_found");
            }
        );
    }

    public String signUpUser(User user) {
        boolean isEmailExist = userRepository.findByEmail(user.getEmail()).isPresent();
        boolean isPhoneExist = userRepository.findByPhone(user.getUsername()).isPresent();

        if(isEmailExist){
            throw new IllegalStateException(String.format(EXIST_EMAIL_EXCEPTION, user.getEmail()));
        }
        if(isPhoneExist)
        {
            throw new IllegalStateException(String.format(EXIST_PHONE_EXCEPTION, user.getPhone()));
        }
        String encryptedPwd = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPwd);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO : SEND EMAIL

        return token;
    }
}
