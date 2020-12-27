package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name="my_user")
@Data
@PropertySource(value = {"classpath:application.properties", "classpath:messages.properties"})
@NoArgsConstructor
public final class User {
    @Id
    @SequenceGenerator(name = "USER_SEQUENCE", sequenceName = "my_user_user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "USER_SEQUENCE")
    @Column(name="user_id")
    private Long userId;

    private String email;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    private String password;

    @Transient
    private String rpassword;

    private String phone;

    @Column(name="date_created")
    @CreationTimestamp
    private Date dateCreated;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(targetEntity = Order.class, mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    //@JsonManagedReference
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(targetEntity = Notification.class, mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    //@JsonManagedReference
    private List<Notification> notifications = new ArrayList<>();

    public String displayRoles() {
        StringBuilder s = new StringBuilder();
        for (Role role: roles) {
            String r = role.getName();
            if (r.equals("COLLECTOR"))
                r = "WORKER";
            s.append(r).append("\n");
        }
        return s.toString();
    }

    public boolean hasRole(String s) {
        for(Role role: roles) {
            if(role.getName().equals(s))
                return true;
        }
        return false;
    }

    public String formattedDate() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy " + "\n" + "kk:mm");
        return formatter.format(this.dateCreated);
    }

    public void copyUser(User user2) {
        this.setEmail(user2.getEmail());
        this.setPhone(user2.getPhone());
        this.setLastName(user2.getLastName());
        this.setFirstName(user2.getFirstName());
        this.setPassword(user2.getPassword());
        this.setNotifications(user2.getNotifications());
        this.setUsername(user2.getUsername());
        this.setOrderList(user2.getOrderList());
    }
}
