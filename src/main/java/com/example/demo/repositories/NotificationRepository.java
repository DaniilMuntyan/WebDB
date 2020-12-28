package com.example.demo.repositories;

import com.example.demo.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(value="SELECT * FROM notification ORDER BY date_created DESC", nativeQuery = true)
    Page<Notification> findAll(Pageable pageable);

    @Query(value="SELECT * FROM notification WHERE user_id = ?1 ORDER BY date_created DESC", nativeQuery = true)
    Page<Notification> findAllByUserId(Long userId, Pageable pageable);
}
