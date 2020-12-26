package com.example.demo.repositories;

import com.example.demo.domain.Camera;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);

    @Query(value="SELECT * FROM my_order ORDER BY date_created DESC", nativeQuery = true)
    Page<Order> findAll(Pageable pageable);

    @Query(value="SELECT * FROM my_order WHERE user_id = ?1 ORDER BY date_created DESC", nativeQuery = true)
    Page<Order> findAllByUserId(Long userId, Pageable pageable);

    @Query(value="SELECT * FROM my_order WHERE status = ?1 ORDER BY date_created DESC", nativeQuery = true)
    Page<Order> findAllByStatus(String status, Pageable pageable);
}
