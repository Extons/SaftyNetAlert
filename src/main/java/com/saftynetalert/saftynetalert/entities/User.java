package com.saftynetalert.saftynetalert.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

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

    @Enumerated
    @Column(name = "role")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;

    @OneToOne
    @JoinColumn(referencedColumnName = "id" , name = "record_fk")
    private MedicalRecord medicalRecord;

}
