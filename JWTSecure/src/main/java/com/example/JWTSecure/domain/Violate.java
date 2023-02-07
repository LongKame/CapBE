package com.example.JWTSecure.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "violate")
public class Violate {

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
    @Column(name="student_post_id")
    private Long studentPostId;
    @Column(name="student_violated_id")
    private Long studentViolatedId;
    @Column(name="name")
    private String name;
    @Column(name="image")
    private String image;
}
