package com.example.JWTSecure.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class StudentInEventDTO implements Serializable{

    private Long id;
    private Long studentId;
    private Long eventId;
    private String username;
    private String caption;
    private String image;
}
