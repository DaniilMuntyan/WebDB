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
    @SequenceGenerator(name = "ORDER_SEQUENCE", sequenceName = "my_order_order_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ORDER_SEQUENCE")
    @Column(name="order_id")
    private Long orderId;
    private String color;
    private String dimensions;
    private Integer focalLength;
    private Integer resolution;
    private Integer amount;

    @Column(name="status")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

}
