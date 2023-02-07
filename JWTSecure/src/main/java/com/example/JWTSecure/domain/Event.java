package com.example.JWTSecure.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @SequenceGenerator(
            name = "event_sequence",
            sequenceName = "event_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "event_sequence"
    )
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="image")
    private String image;
    @Column(name="from_date")
    private LocalDate from_date;
    @Column(name="to_date")
    private LocalDate to_date;
}
