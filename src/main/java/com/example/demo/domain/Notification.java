package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="notification")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Notification {
    @Id
    @SequenceGenerator(name = "NOTIFICATION_SEQUENCE", sequenceName = "notification_notification_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "NOTIFICATION_SEQUENCE")
    @Column(name="notification_id")
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    @NonNull
    private User user;

    @OneToOne
    @JoinColumn(name="order_id", referencedColumnName = "order_id")
    @NonNull
    private Order order;

    @NonNull
    private String message;
}
