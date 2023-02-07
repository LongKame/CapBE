package com.example.JWTSecure.repo;

import com.example.JWTSecure.domain.StudentInEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInEventRepo extends JpaRepository<StudentInEvent, Long> {

    StudentInEvent findByStudentIdAndEventId(Long sid, Long eid);
}