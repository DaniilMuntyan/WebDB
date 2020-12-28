package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name="camera")
@Data
@NoArgsConstructor
public final class Camera {
    @Id
    @SequenceGenerator(name = "CAMERA_SEQUENCE", sequenceName = "camera_camera_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CAMERA_SEQUENCE")
    @Column(name="camera_id")
    private Long cameraId;

    private String color;

    private String dimensions;

    private Integer focalLength;

    private Integer resolution;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="order_id", referencedColumnName = "order_id")
    private Order order;
}
