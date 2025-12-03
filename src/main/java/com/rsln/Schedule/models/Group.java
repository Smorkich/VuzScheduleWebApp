package com.rsln.Schedule.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "student_group")
public class Group extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private int course;
}
