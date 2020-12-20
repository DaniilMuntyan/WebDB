package com.example.demo.repositories;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //@Query("SELECT * FROM my_role r WHERE r.name = ?1")
    Optional<Role> findByName(String username);
}
