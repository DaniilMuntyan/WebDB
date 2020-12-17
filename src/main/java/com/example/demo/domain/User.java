package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="my_user")
@Data
@NoArgsConstructor
public final class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotNull(message = "Укажите адресс электронной почты")
    @Email
    private String email;

    @NotNull(message = "Укажите имя")
    private String first_name;

    @NotNull(message = "Укажите фамилию")
    private String last_name;

    @Column(unique = true)
    @NotNull(message = "Требуется логин")
    private String login;

    @NotNull(message = "Требуется пароль")
    private String password;

    @NotNull(message = "Введите номер телефона")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private Role userRole;

    @OneToMany(targetEntity=Order.class, mappedBy="user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orderList = new ArrayList<>();

}
