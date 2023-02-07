package com.example.JWTSecure.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "student_in_event")
public class StudentInEvent {

    @Id
    @SequenceGenerator(
            name = "student_in_class_sequence",
            sequenceName = "student_in_class_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "student_in_class_sequence"
    )
    private Long id;
    @Column(name="student_id")
    private Long studentId;
    @Column(name="event_id")
    private Long eventId;
    @Column(name="register_date")
    private LocalDate registerDate;
    @Column(name="caption")
    private String caption;
    @Column(name="image")
    private String image;
    @Column(name="state")
    private boolean state;
}
