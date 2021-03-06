package com.example.demo.repositories;

import com.example.demo.domain.Camera;
import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value="SELECT * FROM my_user ORDER BY date_created DESC", nativeQuery = true)
    Page<User> findAll(Pageable pageable);
}
