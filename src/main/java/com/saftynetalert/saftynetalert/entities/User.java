package com.saftynetalert.saftynetalert.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column(name = "birthdate", nullable = false)
    private Date birthdate;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "record_fk")
    private MedicalRecord medicalRecord;

    public User(final String email, final String firstname, final String lastname, final Date birthdate, final String phone, final String encode) {
        this.email = email;
        this.password = encode;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.phone = phone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
