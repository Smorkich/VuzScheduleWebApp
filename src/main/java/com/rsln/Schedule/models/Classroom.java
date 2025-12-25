package com.rsln.Schedule.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "classrooms")
public class Classroom extends BaseEntity {
    @Column(nullable = false)
    private String name;

    public Classroom() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
