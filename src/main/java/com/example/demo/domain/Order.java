package com.example.demo.domain;

import com.example.demo.dto.NewOrderDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="my_order")
@Data
@ToString(exclude = {"user"})
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

    @Column(name="date_created")
    @CreationTimestamp
    private Date date_created;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="collector_id", referencedColumnName = "user_id")
    private User collector;

    public Order(NewOrderDto newOrderDto) {
        this.user = newOrderDto.getUser();
        this.color = newOrderDto.getColor();
        this.dimensions = newOrderDto.getDimensions();
        this.focalLength = newOrderDto.getFocalLength();
        this.orderStatus = newOrderDto.getOrderStatus();
        this.resolution = newOrderDto.getResolution();
        this.amount = newOrderDto.getAmount();
    }
    
    public String getStatus() {
        return this.orderStatus.name();
    }
}
