package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="camera")
@Data
@NoArgsConstructor
public final class Camera {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cameraId;

    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="orderId", referencedColumnName = "orderId")
    private Order order;

    @ManyToMany(mappedBy = "cameras")
    private Set<Part> parts;

}
