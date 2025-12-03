package com.rsln.Schedule.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "teachers")
public class Teacher extends BaseEntity {
    @Column(nullable = false)
    private String fullName;
    private String department;
}
