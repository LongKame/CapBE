package com.example.JWTSecure.controller;

import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.Event;
import com.example.JWTSecure.domain.User;
import com.example.JWTSecure.domain.Violate;
import com.example.JWTSecure.service.*;
import com.example.JWTSecure.service.impl.ResetPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
public class CommonResource {


    private final StudentService studentService;
    private final ResetPassword resetPassword;

    @PostMapping("/get_self_violate")
    public ResponseEntity<List<Violate>> getSelfViolate(@RequestBody User user) {
        return ResponseEntity.ok().body(studentService.getSelfViolate(user));
    }

    @PostMapping("/get_profile_student")
    public ResponseEntity<StudentDTO> getProfile(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.getProfileStudent(studentDTO));
    }
    @PutMapping("/edit_profile_student")
    public ResponseEntity<ResponseStatus> editTeacher(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.editStudentByStudent(studentDTO));
    }

    @PostMapping("/reset_password")
    public String resetPassword(@RequestBody RegistrationRequest request) {
        return resetPassword.reset(request);
    }

    @GetMapping(path = "/confirm_reset_password")
    public String confirm(@RequestParam("token") String token, @RequestParam("user_id") Long user_id) {
        return resetPassword.confirmToken(token, user_id);
    }

    @PostMapping("/save_new_password")
    public String saveNewPassword(@RequestParam("password") String password, @RequestParam("user_id") Long user_id) {
        return resetPassword.saveNewPassword(password, user_id);
    }

    @PostMapping("/change_password")
    public ResponseStatus changePassword(@RequestBody ChangePassword changePassword) {
        return resetPassword.changePassword(changePassword);
    }

    @GetMapping("/get_event")
    public List<Event> getEvent() {
        return studentService.getEvent();
    }

    @PostMapping("/register_event")
    public ResponseEntity<ResponseStatus> registerEvent(@RequestBody StudentInEventDTO studentInEventDTO) {
        return ResponseEntity.ok().body(studentService.addStudentInEvent(studentInEventDTO));
    }

    @PostMapping("/add_violate")
    public ResponseEntity<ResponseStatus> addViolate(@RequestBody ViolateDTO violate) {
        return ResponseEntity.ok().body(studentService.addViolate(violate));
    }

}
