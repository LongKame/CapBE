package com.example.JWTSecure.controller;

import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.Event;
import com.example.JWTSecure.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminResource {

    private final StudentService studentService;

    @PostMapping("/add_student")
    public ResponseEntity<ResponseStatus> addStudent(@RequestBody AddStudentDTO addStudentDTO) {
        return ResponseEntity.ok().body(studentService.addStudent(addStudentDTO));
    }

    @PutMapping("/deactive_student")
    public ResponseEntity<ResponseStatus> deactiveStudent(@RequestBody AddStudentDTO addStudentDTO) {
        return ResponseEntity.ok().body(studentService.deactiveStudent(addStudentDTO));
    }

    @PostMapping("/get_student")
    public ResponseEntity<SearchResultDTO<StudentDTO>> getStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.getAllStudent(studentDTO));
    }

    @PostMapping("/add_event")
    public ResponseEntity<ResponseStatus> addEvent(@RequestBody EventDTO eventDTO) {
            return ResponseEntity.ok().body(studentService.addEvent(eventDTO));
    }

    @PutMapping("/edit_event")
    public ResponseEntity<ResponseStatus> editEvent(@RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok().body(studentService.editEvent(eventDTO));
    }

    @PostMapping("/get_event")
    public ResponseEntity<SearchResultDTO<EventDTO>> getEvent(@RequestBody EventDTO event) {
        return ResponseEntity.ok().body(studentService.getEvent(event));
    }

    @PostMapping("/get_violate")
    public ResponseEntity<SearchResultDTO<ViolateDTO>> getEvent(@RequestBody ViolateDTO violateDTO) {
        return ResponseEntity.ok().body(studentService.getViolate(violateDTO));
    }

    @PostMapping("/get_eventdetail")
    public ResponseEntity<SearchResultDTO<DetailEventDTO>> getEventDetail(@RequestBody DetailEventDTO detailEventDTO) {
        return ResponseEntity.ok().body(studentService.getEventDetail(detailEventDTO));
    }
}
