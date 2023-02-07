package com.example.JWTSecure.repo;

import com.example.JWTSecure.domain.User;
import com.example.JWTSecure.domain.Violate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViolateRepo extends JpaRepository<Violate, Long> {

    List<Violate> findByStudentViolatedId(Long id);
}
