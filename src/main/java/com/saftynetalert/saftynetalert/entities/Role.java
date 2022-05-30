package com.saftynetalert.saftynetalert.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author ufhopla
 * on 30/05/2022.
 */

@Getter
@Setter
@Entity
@NoArgsConstructor

@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public ERole getName() {
        return name;
    }
    public void setName(ERole name) {
        this.name = name;
    }
}
