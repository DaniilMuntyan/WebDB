package com.example.demo.repositories;

import com.example.demo.domain.Order;
import com.example.demo.domain.Task;
import com.example.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value="SELECT * FROM task WHERE collector_id = ?1 ORDER BY date_created DESC", nativeQuery = true)
    Page<Task> findAllOrdersByCollectorId(Long collectorId, Pageable pageable);
}
