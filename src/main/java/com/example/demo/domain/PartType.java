package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="part_type")
@Data
@NoArgsConstructor
public final class PartType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long typeId;

    @NotNull(message = "Тип детали не может быть без названия")
    private String typeName;
}
