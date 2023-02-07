package com.example.JWTSecure.service;

import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.domain.Event;
import com.example.JWTSecure.domain.StudentInEvent;
import com.example.JWTSecure.domain.User;
import com.example.JWTSecure.domain.Violate;

import java.util.List;


public interface StudentService {
    StudentDTO getStudent(StudentDTO studentDTO);

    SearchResultDTO<StudentDTO> getAllStudent(StudentDTO studentDTO);

    ResponseStatus addStudent(AddStudentDTO addStudentDTO);

    ResponseStatus editStudent(AddStudentDTO addStudentDTO);

    StudentDTO getProfileStudent(StudentDTO studentDTO);

    ResponseStatus editStudentByStudent(StudentDTO studentDTO);

    ResponseStatus addEvent(EventDTO eventDTO);

    ResponseStatus editEvent(EventDTO eventDTO);

    ResponseStatus addViolate(ViolateDTO violate);

    ResponseStatus deactiveStudent(AddStudentDTO addStudentDTO);

    SearchResultDTO<EventDTO> getEvent(EventDTO event);

    List<Event> getEvent();

    ResponseStatus addStudentInEvent(StudentInEventDTO studentInEventDTO);

    SearchResultDTO<ViolateDTO> getViolate(ViolateDTO violateDTO);

    SearchResultDTO<DetailEventDTO> getEventDetail(DetailEventDTO detailEventDTO);

    List<Violate> getSelfViolate(User user);
}