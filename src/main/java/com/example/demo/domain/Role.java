package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name="my_role")
@Data
@NoArgsConstructor
public final class Role {
    @Id
    @SequenceGenerator(name = "ROLE_SEQUENCE", sequenceName = "my_role_role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ROLE_SEQUENCE")
    @Column(name="role_id")
    private Long roleId;

    @NotNull
    private String name;

    public Role(String name) {
        this.name = name;
    }

}
