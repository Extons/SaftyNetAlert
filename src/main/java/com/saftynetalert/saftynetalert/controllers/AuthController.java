package com.saftynetalert.saftynetalert.controllers;

import com.saftynetalert.saftynetalert.entities.ERole;
import com.saftynetalert.saftynetalert.entities.Role;
import com.saftynetalert.saftynetalert.entities.User;
import com.saftynetalert.saftynetalert.payload.request.LoginRequest;
import com.saftynetalert.saftynetalert.payload.request.SignupRequest;
import com.saftynetalert.saftynetalert.payload.response.JwtResponse;
import com.saftynetalert.saftynetalert.payload.response.MessageResponse;
import com.saftynetalert.saftynetalert.repositories.RoleRepository;
import com.saftynetalert.saftynetalert.repositories.UserRepository;
import com.saftynetalert.saftynetalert.security.jwt.JwtUtils;
import com.saftynetalert.saftynetalert.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ufhopla
 * on 30/05/2022.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getPassword(),
                roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getEmail(),
                signUpRequest.getFirstname(),
                signUpRequest.getLastname(),
                signUpRequest.getBirthdate(),
                signUpRequest.getPhone(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.CITIZEN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "super":
                        Role medicalAssistRole = roleRepository.findByName(ERole.MEDICAL_ASSIST)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(medicalAssistRole);
                        break;
                    case "mod":
                        Role moderatorRole = roleRepository.findByName(ERole.MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(moderatorRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.CITIZEN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
