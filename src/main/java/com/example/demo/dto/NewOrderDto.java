package com.example.demo.dto;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderDto {
    private String color;
    private String dimensions;
    private Integer focalLength;
    private Integer resolution;
    private Integer amount;
    private OrderStatus orderStatus;
    private User user;

    public NewOrderDto(User user) {
        this.user = user;
        this.orderStatus = OrderStatus.CREATING;
    }

}
