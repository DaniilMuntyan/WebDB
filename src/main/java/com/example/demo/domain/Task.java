package com.example.demo.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="task")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Task {
    @Id
    @SequenceGenerator(name = "TASK_SEQUENCE", sequenceName = "task_task_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TASK_SEQUENCE")
    @Column(name="task_id")
    private Long taskId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="order_id", referencedColumnName = "order_id")
    @NonNull
    private Order order;

    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull
    @JoinColumn(name="collector_id", referencedColumnName = "user_id")
    private User collector;

    @NonNull
    @Column(name="status")
    private TaskStatus taskStatus;

    @Column(name="date_created")
    @CreationTimestamp
    private Date dateCreated;

    public String getStatus() {
        return this.taskStatus.name();
    }
}
