package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="part")
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long partId;

    private String color;

    // REGEX
    private String dimensions;

    @ManyToOne
    @JoinColumn(name = "typeId", referencedColumnName = "typeId")
    private PartType partType;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "part_camera",
            joinColumns = @JoinColumn(name = "partId", referencedColumnName = "partId"),
            inverseJoinColumns = @JoinColumn(name = "cameraId", referencedColumnName = "cameraId"))
    private Set<Camera> cameras;

}
