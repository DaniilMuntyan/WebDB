package com.example.demo.repositories;

import com.example.demo.domain.Camera;
import com.example.demo.domain.PartType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartTypeRepository extends JpaRepository<PartType, Long> {

}
