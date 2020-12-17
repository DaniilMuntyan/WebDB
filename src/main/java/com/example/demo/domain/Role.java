package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="my_role")
@Data
@NoArgsConstructor
public final class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;
    private RoleName roleName;

    @OneToMany(targetEntity=User.class, mappedBy="userRole", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users;


    public enum RoleName {
        MANAGER(0), CLIENT (1), COLLECTOR (2);

        private final int value;
        RoleName(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }


}
