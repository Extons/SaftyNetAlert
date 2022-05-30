package com.saftynetalert.saftynetalert.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "alert")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @Column(name = "alert_type")
    private EAlertType type;

    @ManyToOne(fetch = FetchType.EAGER)
    private Firestation firestation;

}
