package com.saftynetalert.saftynetalert.entities;

import com.saftynetalert.saftynetalert.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name" , nullable = false)
    private String firstname;

    @Column(name = "last_name" , nullable = false)
    private String lastname;

    @Column(name = "birthdate" , nullable = false)
    private Date birthdate;

    @Column(name = "phone" , nullable = false)
    private String phone;

    @Column(name = "email" , nullable = false)
    private String email;

    @Column(name = "password" , nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role Role;

    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;

    @OneToOne
    @JoinColumn(referencedColumnName = "id" , name = "record_fk")
    private MedicalRecord medicalRecord;

    public User(String firstname, String lastname, Date birthdate, String phone, String email, String password,MedicalRecord medicalRecord, Address address, Role Role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.Role = Role;
        this.address = address;
        this.medicalRecord = medicalRecord;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Role.name());
        return Collections.singleton(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

}
