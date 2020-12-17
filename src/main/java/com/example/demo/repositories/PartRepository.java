package com.example.demo.repositories;

import com.example.demo.domain.Camera;
import com.example.demo.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

}
