package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="notification")
@Data
@NoArgsConstructor
public class Notification {
    @Id
    @SequenceGenerator(name = "NOTIFICATION_SEQUENCE", sequenceName = "notification_notification_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "NOTIFICATION_SEQUENCE")
    @Column(name="notification_id")
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    private String message;

}
