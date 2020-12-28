package com.example.demo.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="notification")
@Data
@ToString(exclude = {"user"})
@NoArgsConstructor
@RequiredArgsConstructor
public class Notification {
    @Id
    @SequenceGenerator(name = "NOTIFICATION_SEQUENCE", sequenceName = "notification_notification_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "NOTIFICATION_SEQUENCE")
    @Column(name="notification_id")
    private Long notificationId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    @NonNull
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="order_id", referencedColumnName = "order_id")
    @NonNull
    private Order order;

    @Column(name="manager_name")
    @NonNull
    private String managerName;

    @Column(name="manager_email")
    @NonNull
    private String managerEmail;

    @NonNull
    private String message;

    @Column(name="date_created")
    @CreationTimestamp
    private Date dateCreated;
}
