package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="my_order")
@Data
@NoArgsConstructor
public final class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @NotNull(message = "Укажите цвет")
    private String color;
    @NotNull(message = "Укажите размер в формате AxBxC")
    // REGEX
    private String dimensions;
    @NotNull(message = "Укажите фокусное расстояние")
    private Integer focalLength;
    @NotNull(message = "Укажите разрешение")
    private Integer resolution;
    @NotNull(message = "Укажите количество камер")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "userId")
    private User user;

}
