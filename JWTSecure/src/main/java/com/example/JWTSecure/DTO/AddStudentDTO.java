package com.example.JWTSecure.DTO;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AddStudentDTO {

    private Long id;
    private String username;
    private String fullname;
    private String image;
    private String password;
    private String code;
    private String email;
    private String phone;
    private String address;
    private String violated_code;
    private Long classId;
    private String classname;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String message;
    private boolean state;
}