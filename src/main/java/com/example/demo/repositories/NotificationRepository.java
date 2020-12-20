package com.example.demo.repositories;

import com.example.demo.domain.Camera;
import com.example.demo.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
